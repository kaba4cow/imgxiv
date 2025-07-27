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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER, TYPE_USE })
@Constraint(validatedBy = {})
@Size(min = 2, message = "Category name is too short (min 2 characters)")
@Size(max = 32, message = "Category name is too long (max 32 characters)")
@Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Category name must contain only letters, digits and underscores")
public @interface ValidCategoryName {

	String message() default "Invalid category name";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
