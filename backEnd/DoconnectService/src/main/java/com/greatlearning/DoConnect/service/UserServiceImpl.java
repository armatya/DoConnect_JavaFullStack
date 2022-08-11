package com.greatlearning.DoConnect.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.greatlearning.DoConnect.dto.AskQuestionDTO;
import com.greatlearning.DoConnect.dto.PostAnswerDTO;
import com.greatlearning.DoConnect.entity.Admin;
import com.greatlearning.DoConnect.entity.Answer;
import com.greatlearning.DoConnect.entity.ImageModel;
import com.greatlearning.DoConnect.entity.Question;
import com.greatlearning.DoConnect.entity.User;
import com.greatlearning.DoConnect.exception.AlreadyThere;
import com.greatlearning.DoConnect.exception.NotFound;
import com.greatlearning.DoConnect.repository.IAdminRepo;
import com.greatlearning.DoConnect.repository.IAnswerRepo;
import com.greatlearning.DoConnect.repository.IImageModelRepo;
import com.greatlearning.DoConnect.repository.IQuestionRepo;
import com.greatlearning.DoConnect.repository.IUserRepo;
import com.greatlearning.DoConnect.util.EmailSenderService;
import com.greatlearning.DoConnect.vo.Message;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepo userRepo;
	
	@Autowired
	private IAdminRepo adminRepo;

	@Autowired
	private IQuestionRepo questionRepo;

	@Autowired
	private IAnswerRepo answerRepo;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private IImageModelRepo imageModelRepo;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private EmailSenderService emailSenderService;

	@Override
	public User userLogin(String email, String password) 
	{

		User user = userRepo.findByEmail(email);
		if (Objects.isNull(user))
			throw new NotFound();

		if (user.getPassword().equals(password)) {
			user.setIsActive(true);
			userRepo.save(user);
		} else
			throw new NotFound();
		return user;
	}
	
	@Override
	public Admin adminLogin(String email, String password) 
	{

		Admin admin = adminRepo.findByEmail(email);
		if (Objects.isNull(admin))
			throw new NotFound();

		if (admin.getPassword().equals(password)) {
			admin.setIsActive(true);
			adminRepo.save(admin);
		} else
			throw new NotFound();
		return admin;
	}

	@Override
	public String userLogout(Long userId) 
	{

		User user = userRepo.findById(userId).orElseThrow(() -> new NotFound("User Not Found" + userId));
		user.setIsActive(false);
		userRepo.save(user);

		return "Logged Out";
	}

//	@Override
//	public User userRegister(User user) 
//	{
//
//		User user1 = userRepo.findByEmail(user.getEmail());
//		if (Objects.isNull(user1))
//			return userRepo.save(user);
//
//		throw new AlreadyThere();
//	}
	
	@Override
	public User userRegister(User user,String isForm) {
		
		User user1 = userRepo.findByEmail(user.getEmail());
		if (Objects.isNull(user1))
			return userRepo.save(user);
		if(isForm.equalsIgnoreCase("update"))
		{
			user1.setIsActive(user.getIsActive());
			user1.setName(user.getName());
			user1.setPassword(user.getPassword());
			user1.setPhoneNumber(user.getPhoneNumber());
			System.out.println("name = "+user1.getName()+user1.getEmail()+user1.getPassword()+user1.getPhoneNumber());
			return userRepo.save(user1);
		}
		throw new AlreadyThere();
	}
	@Override
	public Question askQuestion(AskQuestionDTO askQuestionDTO)
	{
		Question question = new Question();

		User user = userRepo.findById(askQuestionDTO.getUserId()).orElseThrow(() -> new NotFound("User Not Found"));
		question.setQuestion(askQuestionDTO.getQuestion());
		question.setTopic(askQuestionDTO.getTopic());
		question.setUser(user);
		if(askQuestionDTO.isImage()==true)
		{
			String s1="SELECT i FROM ImageModel i ORDER BY id DESC";
			ImageModel result = entityManager.createQuery(s1, ImageModel.class).setMaxResults(1).getSingleResult();
			System.out.println("result ID: "+result.getId());
//			question.setImageId(result.getId());
			question.setPicByte(result.getPicByte());
		}
		questionRepo.save(question);
		
		
		List<Admin> admins = adminRepo.findAll();
		for (Admin admin : admins) {
			sendMail(admin.getEmail(), "A new Question ' " +question.getQuestion()+" ' is pending to be approved ");
		}
		return question;
		
	}

	@Override
	public Answer giveAnswer(@Valid PostAnswerDTO postAnswerDTO) 
	{
		Answer answer = new Answer();
		User answerUser = userRepo.findById(postAnswerDTO.getUserId())
				.orElseThrow(() -> new NotFound("User Not Found"));

		Question question = questionRepo.findById(postAnswerDTO.getQuestionId())
				.orElseThrow(() -> new NotFound("Question Not Found"));
		answer.setQuestion(question);
		answer.setAnswer(postAnswerDTO.getAnswer());
		answer.setAnswerUser(answerUser);
        answerRepo.save(answer);
        
        
        List<Admin> admins = adminRepo.findAll();
		for (Admin admin : admins) {
			sendMail(admin.getEmail(), "The Answer to this Question ' "+question.getQuestion()+" ' is posted and waiting for your Approval");
		}
		return answer;
	}
                                                  
	@Override
	public List<Question> searchQuestion(String question) 
	{

		String sqlQuery = "from Question where (question like :question) and isApproved = 1";
		return entityManager.createQuery(sqlQuery, Question.class).setParameter("question", "%" + question + "%")
				.getResultList();
	}

	@Override
	public List<Answer> getAnswers(Long questionId) 
	{
		return answerRepo.findByQuestionId(questionId);
	}

	@Override
	public List<Question> getQuestions(String topic) {
		List<Question> listOfQuestions=new ArrayList<Question>();
		if (topic.equalsIgnoreCase("All")) {
			listOfQuestions= questionRepo.findByIsApprovedTrue();
		}else
		listOfQuestions= questionRepo.findByTopicAndApproved(topic);
		for(int i=0;i<listOfQuestions.size();i++) {
			Question que=listOfQuestions.get(i);
			if(que.getPicByte()!=null)
			que.setPicByte(decompressBytes(que.getPicByte()));
			listOfQuestions.set(i,que);
		}
		return listOfQuestions;
	}

	@Override
	public boolean uplaodImage(MultipartFile file) throws IOException 
	{
		System.out.println("Original Image Byte Size - " + file.getBytes().length);
		ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
				compressBytes(file.getBytes()));
		imageModelRepo.save(img);
		return true;
	}

	@Override
	public ImageModel getImage(String imageName) 
	{
		final Optional<ImageModel> retrievedImage = imageModelRepo.findByName(imageName);
		ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),
				decompressBytes(retrievedImage.get().getPicByte()));
		return img;
	}

	// compress the image bytes before storing it in the database
	public static byte[] compressBytes(byte[] data) 
	{
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}

	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressBytes(byte[] data) 
	{
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}

	@Override
	public Message sendMessage(@Valid Message message) 
	{

		String url = "http://localhost:9595/chat/sendMessage";
		ResponseEntity<Message> responseEntity = restTemplate.postForEntity(url, message, Message.class);
		Message response = responseEntity.getBody();

		return response;
	}
	public Boolean sendMail(String emailId, String type) {
		try {
			emailSenderService.sendEmail(emailId, type, type);
			return true;
		} catch (Exception e) {
			System.out.println("error in sending mail " + e);
			return false;
		}
	}
        //Function for sending the password to the respective user who forgot it.
	@Override
	public boolean forgetPassword(String email) 
	{
		
		User user = userRepo.findByEmail(email);
		if (Objects.isNull(user))
			return false;
		sendMail(user.getEmail(), "Your old password is ' "+user.getPassword()+" '.\n Please Edit your password after login");
		return true;
	}

	
}
