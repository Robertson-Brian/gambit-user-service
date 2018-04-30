package com.revature.gambit.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends RuntimeException{


	private static final long serialVersionUID = 4898166106104495770L;
	
	private HttpStatus status;
	public InvalidInputException(String message, HttpStatus status){
		super(message);
		this.status=status;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	

}
