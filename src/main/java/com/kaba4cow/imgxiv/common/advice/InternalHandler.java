package com.kaba4cow.imgxiv.common.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kaba4cow.imgxiv.common.advice.handler.ExceptionHandlerResponseEntity;
import com.kaba4cow.imgxiv.common.exception.ImageUploadException;

@Order(Ordered.LOWEST_PRECEDENCE)
@ControllerAdvice
public class InternalHandler extends AbstractExceptionHandler {

	@ExceptionHandler(ImageUploadException.class)
	public ExceptionHandlerResponseEntity handleImageUpload(ImageUploadException exception) {
		return defaultResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception);
	}

	@ExceptionHandler(Exception.class)
	public ExceptionHandlerResponseEntity handleFallback(Exception exception) {
		return defaultResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
	}

}
