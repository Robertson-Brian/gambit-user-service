package com.revature.gambit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Last updated by: (1802-Matt)
 * 
 * @author Unknown 
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthUserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final String message;
	private final HttpStatus httpStatus;

	/**
	 * Last updated by: (1802-Matt)
	 * 
	 * @author Unknown
	 * * Gambit Integrators:
	 * * @author Mark Fleres
	 * 
	 * @param message
	 * @param HttpStatus
	 */
	public AuthUserException(String message, HttpStatus httpStatus) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
