package com.revature.gambit.exceptions;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.revature.gambit.ajax.ClientMessage;

@ControllerAdvice
public class GlobalHandler {
	private static Logger logger = Logger.getLogger(GlobalHandler.class);
	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ClientMessage> handleAnyException(Throwable t){
		logger.error("Fatal Exception");
		return new ResponseEntity<>(new ClientMessage("SOMTHING WRONG") ,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ClientMessage>  handleInvalidInputException(InvalidInputException e){
		logger.error("Invalid Input"+e);
		
		return new ResponseEntity<>(new ClientMessage("INVALID INPUT"),HttpStatus.BAD_REQUEST);
	}

	
}
