package com.kaba4cow.imgxiv.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kaba4cow.imgxiv.common.exception.ConflictException;
import com.kaba4cow.imgxiv.common.exception.NotFoundException;
import com.kaba4cow.imgxiv.common.response.ExceptionHandlerResponseBuilder;
import com.kaba4cow.imgxiv.common.response.ExceptionHandlerResponseEntity;

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
		return defaultResponse(HttpStatus.NOT_FOUND, exception);
	}

	@ExceptionHandler(NotFoundException.class)
	public ExceptionHandlerResponseEntity handleNotFound(NotFoundException exception) {
		return defaultResponse(HttpStatus.NOT_FOUND, exception);
	}

	@ExceptionHandler(ConflictException.class)
	public ExceptionHandlerResponseEntity handleConflictException(ConflictException exception) {
		return defaultResponse(HttpStatus.CONFLICT, exception);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ExceptionHandlerResponseEntity handleAuthenticationException(AuthenticationException exception) {
		return defaultResponse(HttpStatus.UNAUTHORIZED, exception);
	}

	@ExceptionHandler(Exception.class)
	public ExceptionHandlerResponseEntity handleAll(Exception exception) {
		return defaultResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
	}

	private ExceptionHandlerResponseEntity defaultResponse(HttpStatusCode status, Exception exception) {
		return new ExceptionHandlerResponseBuilder()//
				.status(status)//
				.error(exception)//
				.build();
	}

	private ExceptionHandlerResponseEntity defaultResponse(HttpStatusCode status, String error) {
		return new ExceptionHandlerResponseBuilder()//
				.status(status)//
				.error("error", error)//
				.build();
	}

}
