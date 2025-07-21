package com.kaba4cow.imgxiv.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kaba4cow.imgxiv.common.advice.handler.ExceptionHandlerResponseBuilder;
import com.kaba4cow.imgxiv.common.advice.handler.ExceptionHandlerResponseEntity;

@ControllerAdvice
public class BadRequestHandler extends AbstractExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ExceptionHandlerResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
		return new ExceptionHandlerResponseBuilder()//
				.status(HttpStatus.BAD_REQUEST)//
				.errors(exception.getBindingResult().getFieldErrors(), FieldError::getField, FieldError::getDefaultMessage)//
				.build();
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ExceptionHandlerResponseEntity handleIllegalArgument(IllegalArgumentException exception) {
		return defaultResponse(HttpStatus.BAD_REQUEST, exception);
	}

}
