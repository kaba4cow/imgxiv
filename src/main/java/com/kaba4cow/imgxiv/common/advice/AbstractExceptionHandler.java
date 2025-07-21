package com.kaba4cow.imgxiv.common.advice;

import org.springframework.http.HttpStatusCode;

import com.kaba4cow.imgxiv.common.advice.handler.ExceptionHandlerResponseBuilder;
import com.kaba4cow.imgxiv.common.advice.handler.ExceptionHandlerResponseEntity;

public abstract class AbstractExceptionHandler {

	public ExceptionHandlerResponseEntity defaultResponse(HttpStatusCode status, Exception exception) {
		return new ExceptionHandlerResponseBuilder()//
				.status(status)//
				.error(exception)//
				.build();
	}

	public ExceptionHandlerResponseEntity defaultResponse(HttpStatusCode status, String error) {
		return new ExceptionHandlerResponseBuilder()//
				.status(status)//
				.error("error", error)//
				.build();
	}

}
