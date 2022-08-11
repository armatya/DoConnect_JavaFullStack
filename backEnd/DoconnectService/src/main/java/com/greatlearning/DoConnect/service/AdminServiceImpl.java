package com.greatlearning.DoConnect.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatlearning.DoConnect.dto.ResponseDTO;
import com.greatlearning.DoConnect.entity.Admin;
import com.greatlearning.DoConnect.entity.Answer;
import com.greatlearning.DoConnect.entity.Question;
import com.greatlearning.DoConnect.entity.User;
import com.greatlearning.DoConnect.exception.AlreadyThere;
import com.greatlearning.DoConnect.exception.NotFound;
import com.greatlearning.DoConnect.repository.IAdminRepo;
import com.greatlearning.DoConnect.repository.IAnswerRepo;
import com.greatlearning.DoConnect.repository.IQuestionRepo;
import com.greatlearning.DoConnect.repository.IUserRepo;
import com.greatlearning.DoConnect.util.EmailSenderService;

@Service
public class AdminServiceImpl implements IAdminService {

	@Autowired
	private IAdminRepo adminRepo;

	@Autowired
	private IQuestionRepo questionRepo;

	@Autowired
	private IAnswerRepo answerRepo;

	@Autowired
	private IUserRepo userRepo;

	@Autowired
	private EmailSenderService emailSenderService;

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
	public String adminLogout(Long adminId) 
	{

		Admin admin = adminRepo.findById(adminId).orElseThrow(() -> new NotFound("Admin not found"));
		admin.setIsActive(false);
		adminRepo.save(admin);
		return "Logged Out";
	}

	@Override
	public Admin adminRegister(Admin admin) 
	{

		Admin admin1 = adminRepo.findByEmail(admin.getEmail());
		if (Objects.isNull(admin1))
			return adminRepo.save(admin);

		throw new AlreadyThere();
	}

	@Override
	public List<Question> getUnApprovedQuestions()
	{
		return questionRepo.findByIsApproved();
	}

	@Override
	public List<Answer> getUnApprovedAnswers()
	{
		return answerRepo.findByIsApproved();
	}

	@Override
	public Question approveQuestion(Long questionId) 
	{

		Question question = questionRepo.findById(questionId).orElseThrow(() -> new NotFound("Question not found"));
        
		question.setIsApproved(true);
		question = questionRepo.save(question);

		List<User> users = userRepo.findAll();
		for (User user : users) {
			sendMail(user.getEmail(), "A new Question ' "+question.getQuestion()+ " ' has been released in our Website, So hurry up and start Answering. THANK YOU !!!");
		}
		// a mail should go to the list of Admins that the question is approved

		return question;
	}

	@Override
	public Answer approveAnswer(Long answerId) 
	{
		Answer answer = answerRepo.findById(answerId).orElseThrow(() -> new NotFound("Answer not found"));
        
		answer.setIsApproved(true);
		answer = answerRepo.save(answer);
		
		Question question = answer.getQuestion();
		List<User> users = userRepo.findAll();
		for (User user : users) {
			sendMail(user.getEmail(), "The Answer to this Question ' "+question.getQuestion()+" ' is published. Please go have a look. ENJOY !!!");
		}

		// a mail should go to the admin that a answer is published
		return answer;
	}

	@Override
	public ResponseDTO deleteQuestion(Long questionId) 
	{

		ResponseDTO responseDTO = new ResponseDTO();
		Question question = questionRepo.findById(questionId).orElseThrow(() -> new NotFound("Question not found"));

		questionRepo.delete(question);
		responseDTO.setMsg("Question removed");
		return responseDTO;
	}

	@Override
	public ResponseDTO deleteAnswer(Long answerId) 
	{
		ResponseDTO responseDTO = new ResponseDTO();

		Answer answer = answerRepo.findById(answerId).orElseThrow(() -> new NotFound("Answer not found"));

		answerRepo.delete(answer);
		responseDTO.setMsg("Answer Removed");
		return responseDTO;
	}

	public Boolean sendMail(String emailId, String type) 
	{
		try {
			emailSenderService.sendEmail(emailId, type, type);
			return true;
		} catch (Exception e) {
			System.out.println("error in sending mail " + e);
			return false;
		}
	}

	@Override
	public User getUser(String email) 
	{
		return userRepo.findByEmail(email);
	}

	@Override
	public List<User> getAllUser() 
	{
		return userRepo.findAll();
	}

}
