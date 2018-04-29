package com.revature.gambit.exceptions;

import com.revature.gambit.ajax.ClientMessage;

public class InvalidInputException extends RuntimeException{


	private static final long serialVersionUID = 4898166106104495770L;
	
	public InvalidInputException(ClientMessage clientMessage){
		super(clientMessage.getMessage());
	}

}
