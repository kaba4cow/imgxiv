package com.kaba4cow.imgxiv.common;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kaba4cow.imgxiv.common.exception.ConflictException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ExceptionHandlerResponseEntity handleValidationException(MethodArgumentNotValidException exception) {
		return new ExceptionHandlerResponseBuilder()//
				.status(HttpStatus.BAD_REQUEST)//
				.errors(exception.getBindingResult().getFieldErrors(), FieldError::getField, FieldError::getDefaultMessage)//
				.build();
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ExceptionHandlerResponseEntity handleUsernameNotFound(UsernameNotFoundException exception) {
		return new ExceptionHandlerResponseBuilder()//
				.status(HttpStatus.NOT_FOUND)//
				.error(exception)//
				.build();
	}

	@ExceptionHandler(ConflictException.class)
	public ExceptionHandlerResponseEntity handleConflictException(ConflictException exception) {
		return new ExceptionHandlerResponseBuilder()//
				.status(HttpStatus.CONFLICT)//
				.error(exception)//
				.build();
	}

	@ExceptionHandler(AuthenticationException.class)
	public ExceptionHandlerResponseEntity handleAuthenticationException(AuthenticationException exception) {
		return new ExceptionHandlerResponseBuilder()//
				.status(HttpStatus.UNAUTHORIZED)//
				.error(exception)//
				.build();
	}

	@ExceptionHandler(Exception.class)
	public ExceptionHandlerResponseEntity handleAll(Exception exception) {
		return new ExceptionHandlerResponseBuilder()//
				.status(HttpStatus.INTERNAL_SERVER_ERROR)//
				.error("error", "Internal server error")//
				.build();
	}

}
