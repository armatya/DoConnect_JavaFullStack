package com.greatlearning.DoConnect.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AskQuestionDTO {

	@NotNull(message = "provide the id")
	private Long userId;
	
	@NotBlank(message = "Question is required")
	private String question;
	
	@NotBlank(message = "provide the topic")
	private String topic;

	@NotNull(message = "provide the isImage")
	public boolean isImage;
}
