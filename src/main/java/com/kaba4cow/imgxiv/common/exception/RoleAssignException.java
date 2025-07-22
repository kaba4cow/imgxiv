package com.kaba4cow.imgxiv.common.exception;

public class RoleAssignException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RoleAssignException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoleAssignException(String message) {
		super(message);
	}

}
