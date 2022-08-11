package com.greatlearning.DoConnect.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class NotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorMsg;

}
