package com.greatlearning.DoConnect.service;

import java.io.IOException;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.greatlearning.DoConnect.dto.AskQuestionDTO;
import com.greatlearning.DoConnect.dto.PostAnswerDTO;
import com.greatlearning.DoConnect.entity.Admin;
import com.greatlearning.DoConnect.entity.Answer;
import com.greatlearning.DoConnect.entity.ImageModel;
import com.greatlearning.DoConnect.entity.Question;
import com.greatlearning.DoConnect.entity.User;
import com.greatlearning.DoConnect.vo.Message;

public interface IUserService {

	public User userLogin(String email, String password);

	public String userLogout(Long userId);

	public User userRegister(@Valid User user, String isFrom);

	public Question askQuestion(@Valid AskQuestionDTO askQuestionDTO);

	public Answer giveAnswer(@Valid PostAnswerDTO postAnswerDTO);

	public List<Question> searchQuestion(String question);

	public List<Answer> getAnswers(Long questionId);

	public List<Question> getQuestions(String topic);

	public boolean uplaodImage(MultipartFile file) throws IOException;

	public ImageModel getImage(String imageName);

	public Message sendMessage(@Valid Message message);

	Admin adminLogin(String email, String password);

	public boolean forgetPassword(String email);

}
