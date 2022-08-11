package com.greatlearning.DoConnect.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@org.springframework.web.bind.annotation.ExceptionHandler(value = NotFound.class)
	public ResponseEntity<Object> exception(NotFound exception) {
		return new ResponseEntity<>("Invalid ID ---> Not found", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(value = AlreadyThere.class)
	public ResponseEntity<Object> exception2(AlreadyThere exception) {
		return new ResponseEntity<>("Already There --> Must be unique", HttpStatus.BAD_REQUEST);
	}

}
