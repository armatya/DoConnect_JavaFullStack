package com.greatlearning.DoConnect.vo;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

	@NotBlank(message = "frovide the user Details")
	private String fromUser;
	
	@NotBlank(message = "provide message")
	private String message;
}
