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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER, TYPE_USE })
@Constraint(validatedBy = {})
@Size(min = 4, message = "Username is too short (min 4 characters)")
@Size(max = 32, message = "Username is too long (max 32 characters)")
@Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username must contain only letters, digits and underscores")
public @interface ValidUsername {

	String message() default "Invalid username";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
