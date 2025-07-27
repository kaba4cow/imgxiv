package com.kaba4cow.imgxiv.domain.category.validation;

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
@Size(max = 1024, message = "Category description is too long (max 1024 characters)")
public @interface ValidCategoryDescription {

	String message() default "Invalid category description";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
