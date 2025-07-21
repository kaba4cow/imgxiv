package com.kaba4cow.imgxiv.common.validation.jakarta;

import java.util.Objects;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContentTypeValidator implements ConstraintValidator<ContentType, MultipartFile> {

	private Set<String> allowed;

	@Override
	public void initialize(ContentType constraintAnnotation) {
		allowed = Set.of(constraintAnnotation.allowed());
	}

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		return Objects.isNull(file) || file.isEmpty() || allowed.contains(file.getContentType());
	}

}
