package com.kaba4cow.imgxiv.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kaba4cow.imgxiv.common.advice.handler.ExceptionHandlerResponseEntity;

@ControllerAdvice
public class ForbiddenHandler extends AbstractExceptionHandler {

	@ExceptionHandler(AccessDeniedException.class)
	public ExceptionHandlerResponseEntity handleAccessDenied(AccessDeniedException exception) {
		return defaultResponse(HttpStatus.FORBIDDEN, exception);
	}

}
