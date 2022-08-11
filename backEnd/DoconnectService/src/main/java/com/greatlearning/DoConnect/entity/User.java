package com.greatlearning.DoConnect.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "Name is mandatory")
	private String name;
	
	@NotBlank(message = "Password is mandatory")
	@Size(min = 8)
	private String password;
	
	@NotBlank(message = "email is mandatory")
	@Email(message = "Enter a valid mail only")
	private String email;
	
	@NotBlank(message = "phone number is mandatory")
	@Size(max = 10, min = 10)
	private String phoneNumber;

	private Boolean isActive = true;
}
