package com.kaba4cow.imgxiv.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kaba4cow.imgxiv.common.advice.handler.ExceptionHandlerResponseEntity;

@ControllerAdvice
public class UnauthorizedHandler extends AbstractExceptionHandler {

	@ExceptionHandler(UsernameNotFoundException.class)
	public ExceptionHandlerResponseEntity handleUsernameNotFound(UsernameNotFoundException exception) {
		return defaultResponse(HttpStatus.UNAUTHORIZED, exception);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ExceptionHandlerResponseEntity handleAuthentication(AuthenticationException exception) {
		return defaultResponse(HttpStatus.UNAUTHORIZED, exception);
	}

}
