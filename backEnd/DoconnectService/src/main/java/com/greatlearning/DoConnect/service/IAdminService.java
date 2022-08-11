package com.greatlearning.DoConnect.service;

import java.util.List;

import com.greatlearning.DoConnect.dto.ResponseDTO;
import com.greatlearning.DoConnect.entity.Admin;
import com.greatlearning.DoConnect.entity.Answer;
import com.greatlearning.DoConnect.entity.Question;
import com.greatlearning.DoConnect.entity.User;

public interface IAdminService {

	public Admin adminLogin(String email, String password);

	public String adminLogout(Long adminId);

	public Admin adminRegister(Admin admin);

	public List<Question> getUnApprovedQuestions();

	public List<Answer> getUnApprovedAnswers();

	public Question approveQuestion(Long questionId);

	public Answer approveAnswer(Long answerId);

	public ResponseDTO deleteQuestion(Long questionId);

	public ResponseDTO deleteAnswer(Long answerId);

	public User getUser(String email);

	public List<User> getAllUser();

	User userLogin(String email, String password);

}
