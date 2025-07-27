package com.kaba4cow.imgxiv.domain.user.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER, TYPE_USE })
@Constraint(validatedBy = {})
@Size(max = 64, message = "Email is too long (max 64 characters)")
@Email(message = "User email must be a valid email address")
public @interface ValidEmail {

	String message() default "Invalid email";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
