package com.kaba4cow.imgxiv.common.validation;

import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

	private long maxLength;

	@Override
	public void initialize(FileSize constraintAnnotation) {
		maxLength = constraintAnnotation.max();
	}

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		return Objects.isNull(file) || file.isEmpty() || file.getSize() <= maxLength;
	}

}
