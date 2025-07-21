package com.kaba4cow.imgxiv.auth.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Documented
@Retention(RUNTIME)
@Target(PARAMETER)
@AuthenticationPrincipal
public @interface CurrentUser {}
