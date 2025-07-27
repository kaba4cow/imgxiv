package com.kaba4cow.imgxiv.auth.annotation.authority;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

import com.kaba4cow.imgxiv.domain.user.UserAuthorities;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@PreAuthorize("hasAuthority('" + UserAuthorities.MANAGE_TAGS + "')")
public @interface CanManageTags {}
