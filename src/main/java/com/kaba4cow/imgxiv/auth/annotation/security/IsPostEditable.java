package com.kaba4cow.imgxiv.auth.annotation.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@PostAuthorize("returnObject.author.id == principal.id or hasAuthority('edit-post-na')")
public @interface IsPostEditable {}
