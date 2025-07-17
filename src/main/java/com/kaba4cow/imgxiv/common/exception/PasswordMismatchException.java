package com.kaba4cow.imgxiv.common.exception;

public class PasswordMismatchException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public PasswordMismatchException(String message) {
		super(message);
	}

}
