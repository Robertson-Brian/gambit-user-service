package com.revature.gambit.exception;

import com.revature.gambit.ajax.ClientMessage;

public class InvalidInputException extends RuntimeException{

	private static final long serialVersionUID = 4090264805332270994L;

	public InvalidInputException(ClientMessage message){
		super(message.getMessage());
	}
	
}
