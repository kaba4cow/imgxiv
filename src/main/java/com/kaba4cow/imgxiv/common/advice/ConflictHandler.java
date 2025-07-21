package com.kaba4cow.imgxiv.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kaba4cow.imgxiv.common.advice.handler.ExceptionHandlerResponseEntity;
import com.kaba4cow.imgxiv.common.exception.ConflictException;

@ControllerAdvice
public class ConflictHandler extends AbstractExceptionHandler {

	@ExceptionHandler(ConflictException.class)
	public ExceptionHandlerResponseEntity handleConflict(ConflictException exception) {
		return defaultResponse(HttpStatus.CONFLICT, exception);
	}

}
