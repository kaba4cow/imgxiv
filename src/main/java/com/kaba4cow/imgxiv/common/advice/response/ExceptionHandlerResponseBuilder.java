package com.kaba4cow.imgxiv.common.advice.response;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.http.HttpStatusCode;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExceptionHandlerResponseBuilder {

	private final Map<String, Object> errors = new LinkedHashMap<>();

	private HttpStatusCode status;

	public ExceptionHandlerResponseBuilder status(HttpStatusCode status) {
		this.status = status;
		return this;
	}

	public ExceptionHandlerResponseBuilder error(Exception exception) {
		return error("error", exception.getMessage());
	}

	public ExceptionHandlerResponseBuilder error(String error, Object value) {
		errors.put(error, value);
		return this;
	}

	public <T> ExceptionHandlerResponseBuilder errors(Collection<T> errors, Function<T, String> keyMapper,
			Function<T, Object> valueMapper) {
		for (T error : errors)
			error(keyMapper.apply(error), valueMapper.apply(error));
		return this;
	}

	public ExceptionHandlerResponseEntity build() {
		return new ExceptionHandlerResponseEntity(errors, status);
	}

}