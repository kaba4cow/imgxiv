package com.kaba4cow.imgxiv.common.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String element, Object identifier) {
		super(String.format("%s not found: %s", element, identifier));
	}

}
