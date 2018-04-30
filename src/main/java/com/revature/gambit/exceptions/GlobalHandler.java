package com.revature.gambit.exceptions;


import static com.revature.gambit.util.MessagingUtil.INVALID_INPUT;
import static com.revature.gambit.util.MessagingUtil.UNAUTHORIZED_USER;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandler {
	private static Logger logger = Logger.getLogger(GlobalHandler.class);
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<String>  handleInvalidInputException(InvalidInputException e){
		logger.error("Invalid Input"+e);
		
		return new ResponseEntity<>(INVALID_INPUT,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AuthUserException.class)
	public ResponseEntity<String>  handleAuthUserException(AuthUserException e){
		logger.error("Auth User Exception "+e);
		
		return new ResponseEntity<>(UNAUTHORIZED_USER,HttpStatus.UNAUTHORIZED);
	}

}
