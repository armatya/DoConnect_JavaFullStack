package com.greatlearning.DoConnect.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostAnswerDTO {

	@NotNull(message = "provide the user Id")
	private Long userId;
	
	@NotNull(message = "provide the Question Id")
	private Long questionId;
	
	@NotBlank(message = "Answer is needed")
	private String answer;
}
