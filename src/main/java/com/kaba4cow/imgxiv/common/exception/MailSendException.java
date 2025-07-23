package com.kaba4cow.imgxiv.common.exception;

public class MailSendException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MailSendException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailSendException(String message) {
		super(message);
	}

}
