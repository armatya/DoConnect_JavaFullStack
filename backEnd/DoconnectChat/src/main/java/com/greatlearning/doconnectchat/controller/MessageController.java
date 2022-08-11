package com.greatlearning.doconnectchat.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.doconnectchat.dto.MessageDTO;
import com.greatlearning.doconnectchat.service.IMessageService;

@RestController
@RequestMapping("/chat")
@CrossOrigin
public class MessageController {

	@Autowired
	private IMessageService messageService;

	@PostMapping("/sendMessage")
	public MessageDTO sendMessage(@Valid @RequestBody MessageDTO messageDTO) {
		return messageService.sendMessage(messageDTO);
	}

	@GetMapping("/getMessage")
	public List<MessageDTO> getMessage() {
		return messageService.getMessage();
	}

}
