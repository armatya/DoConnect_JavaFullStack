package com.greatlearning.DoConnect.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class ImageModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	// image bytes can have large lengths so we specify a value
	// which is more than the default length for picByte column
	@Column(name = "picByte", length = 1000)
	private byte[] picByte;

	public ImageModel() {
		super();
	}

	public ImageModel(String name, String type, byte[] picByte) {
		this.name = name;
		this.type = type;
		this.picByte = picByte;
	}

}
