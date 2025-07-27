package com.kaba4cow.imgxiv.common.advice.response;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ExceptionHandlerResponseEntity extends ResponseEntity<Map<String, Object>> {

	ExceptionHandlerResponseEntity(Map<String, Object> body, HttpStatusCode status) {
		super(body, new HttpHeaders(), status);
	}

}
