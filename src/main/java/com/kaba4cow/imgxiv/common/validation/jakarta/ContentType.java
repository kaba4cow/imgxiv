package com.kaba4cow.imgxiv.common.validation.jakarta;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
@Constraint(validatedBy = ContentTypeValidator.class)
public @interface ContentType {

	String message();

	String[] allowed();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
