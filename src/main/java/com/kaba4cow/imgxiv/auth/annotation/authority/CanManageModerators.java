package com.kaba4cow.imgxiv.auth.annotation.authority;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;

import com.kaba4cow.imgxiv.domain.user.UserAuthorities;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@PostAuthorize("hasAuthority('" + UserAuthorities.MANAGE_MODERATORS + "')")
public @interface CanManageModerators {}
