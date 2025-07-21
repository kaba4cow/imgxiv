package com.kaba4cow.imgxiv.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kaba4cow.imgxiv.common.advice.handler.ExceptionHandlerResponseEntity;

@ControllerAdvice
public class NotImplementedHandler extends AbstractExceptionHandler {

	@ExceptionHandler(UnsupportedOperationException.class)
	public ExceptionHandlerResponseEntity handleUnsupportedOperation(UnsupportedOperationException exception) {
		return defaultResponse(HttpStatus.NOT_IMPLEMENTED, exception);
	}

}
