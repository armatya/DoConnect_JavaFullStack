package com.greatlearning.DoConnect.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.greatlearning.DoConnect.dto.AskQuestionDTO;
import com.greatlearning.DoConnect.dto.PostAnswerDTO;
import com.greatlearning.DoConnect.entity.Answer;
import com.greatlearning.DoConnect.entity.ImageModel;
import com.greatlearning.DoConnect.entity.Question;
import com.greatlearning.DoConnect.entity.User;
import com.greatlearning.DoConnect.service.IUserService;
import com.greatlearning.DoConnect.vo.Message;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private IUserService userService;

	@GetMapping("/login/{email}/{password}")
	public User userLogin(@PathVariable String email, @PathVariable String password) {
		return userService.userLogin(email, password);
	}

	@GetMapping("/logout/{userId}")
	public String userLogout(@PathVariable Long userId) {
		return userService.userLogout(userId);
	}

	@PostMapping("/register/{isFrom}")
	public User userRegister(@Valid @RequestBody User user,@PathVariable String isFrom) {
		return userService.userRegister(user,isFrom);
	}

	@PostMapping("/askQuestion")
	public Question askQuestion(@Valid @RequestBody AskQuestionDTO askQuestionDTO) {
		return userService.askQuestion(askQuestionDTO);
	}

	@PostMapping("/giveAnswer")
	public Answer giveAnswer(@Valid @RequestBody PostAnswerDTO postAnswerDTO) {
		return userService.giveAnswer(postAnswerDTO);
	}

	@GetMapping("/searchQuestion/{question}")
	public List<Question> searchQuestion(@PathVariable String question) {
		return userService.searchQuestion(question);
	}

	@GetMapping("/getAnswers/{questionId}")
	public List<Answer> getAnswers(@PathVariable Long questionId) {
		return userService.getAnswers(questionId);
	}

	@GetMapping("/getQuestions/{topic}")
	public List<Question> getQuestions(@PathVariable String topic) {
		return userService.getQuestions(topic);
	}

	@PostMapping("/upload")
	public boolean uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
		return userService.uplaodImage(file);
	}
	
	@GetMapping(path = { "/get/{imageName}" })
	public ImageModel getImage(@PathVariable("imageName") String imageName) throws IOException {
		return userService.getImage(imageName);
	}

	@PostMapping("/sendMessage")
	public Message sendMessage(@Valid @RequestBody Message message) {
		return userService.sendMessage(message);
	}
	@GetMapping("/forget/{email}")
	public boolean openEmailForm(@PathVariable String email)
	{
		return userService.forgetPassword(email);
	}
	
}
