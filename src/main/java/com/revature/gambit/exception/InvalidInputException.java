package com.revature.gambit.exception;

public class InvalidInputException extends RuntimeException{


	private static final long serialVersionUID = 4898166106104495770L;
	
	public InvalidInputException(String message){
		super(message);
	}

}
