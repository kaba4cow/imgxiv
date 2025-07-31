package com.kaba4cow.imgxiv.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kaba4cow.imgxiv.common.advice.response.ExceptionHandlerResponseEntity;
import com.kaba4cow.imgxiv.common.exception.RoleAssignException;

@ControllerAdvice
public class ForbiddenHandler extends AbstractExceptionHandler {

	@ExceptionHandler(AccessDeniedException.class)
	public ExceptionHandlerResponseEntity handleAccessDenied(AccessDeniedException exception) {
		return defaultResponse(HttpStatus.FORBIDDEN, exception);
	}

	@ExceptionHandler(RoleAssignException.class)
	public ExceptionHandlerResponseEntity handleRoleAssign(RoleAssignException exception) {
		return defaultResponse(HttpStatus.FORBIDDEN, exception);
	}

}
