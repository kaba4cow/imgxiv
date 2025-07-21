package com.kaba4cow.imgxiv.common.exception;

public class ImageUploadException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ImageUploadException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageUploadException(String message) {
		super(message);
	}

}
