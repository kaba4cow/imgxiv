package com.kaba4cow.imgxiv.auth.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@PreAuthorize("permitAll()")
public @interface PermitAll {}
