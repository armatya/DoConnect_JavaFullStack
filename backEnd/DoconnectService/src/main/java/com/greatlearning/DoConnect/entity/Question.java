package com.greatlearning.DoConnect.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "Question can not be blank")
	private String question;
	
	@OneToOne
	private User user;
	
	@NotBlank(message = "provide the topic")
	private String topic;
	
	private Boolean isApproved = false;
	
	@Column(name = "picByte", length = 1000)
	private byte[] picByte;
}
