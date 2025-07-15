package com.kaba4cow.imgxiv.common.exception;

public class UsernameConflictException extends ConflictException {

	private static final long serialVersionUID = 1L;

	public UsernameConflictException(String message) {
		super(message);
	}

}
