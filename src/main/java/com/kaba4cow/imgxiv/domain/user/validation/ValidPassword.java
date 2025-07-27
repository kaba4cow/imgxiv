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
import jakarta.validation.constraints.Size;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER, TYPE_USE })
@Constraint(validatedBy = {})
@Size(min = 8, message = "Password is too short (min 8 characters)")
@Size(max = 64, message = "Password is too long (max 64 characters)")
public @interface ValidPassword {

	String message() default "Invalid password";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
