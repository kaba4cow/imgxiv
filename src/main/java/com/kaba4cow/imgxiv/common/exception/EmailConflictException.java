package com.kaba4cow.imgxiv.common.exception;

public class EmailConflictException extends ConflictException {

	private static final long serialVersionUID = 1L;

	public EmailConflictException(String message) {
		super(message);
	}

}
