package com.kaba4cow.imgxiv.common.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class PasswordMismatchException extends BadCredentialsException {

	private static final long serialVersionUID = 1L;

	public PasswordMismatchException(String message) {
		super(message);
	}

}
