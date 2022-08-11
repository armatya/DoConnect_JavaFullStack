package com.greatlearning.doconnectchat.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatlearning.doconnectchat.dto.MessageDTO;
import com.greatlearning.doconnectchat.entity.Message;
import com.greatlearning.doconnectchat.repository.IMessageRepo;

@Service
public class IMessageServiceImpl implements IMessageService {

	@Autowired
	private IMessageRepo messageRepo;

	@Override
	public MessageDTO sendMessage(@Valid MessageDTO messageDTO) {

		Message message = new Message();
		message.setMessage(messageDTO.getMessage());
		message.setFromUser(messageDTO.getFromUser());
		message = messageRepo.save(message);

		messageDTO.setFromUser(message.getFromUser());
		messageDTO.setMessage(message.getMessage());

		return messageDTO;
	}

	@Override
	public List<MessageDTO> getMessage() {
		List<MessageDTO> data = new ArrayList<MessageDTO>();

		List<Message> messages = messageRepo.findAll();
		for (Message message : messages) {

			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setFromUser(message.getFromUser());
			messageDTO.setMessage(message.getMessage());
			data.add(messageDTO);

		}

		return data;
	}

}
