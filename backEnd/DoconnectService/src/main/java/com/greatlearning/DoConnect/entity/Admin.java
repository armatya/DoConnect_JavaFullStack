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
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "Name is mandatory")
	private String name;
	
	@NotBlank(message = "password the mandatory")
	@Size(min = 8, message = "length should be 8")
	private String password;
	
	@NotBlank(message = "Email is mandatory")
	@Email(message = "provide a valid mail id only")
	private String email;
	
	@NotBlank(message = "Phone Number is mandatory")
	@Size(max = 10, min = 10)
	private String phoneNumber;

	private Boolean isActive = true;

}
