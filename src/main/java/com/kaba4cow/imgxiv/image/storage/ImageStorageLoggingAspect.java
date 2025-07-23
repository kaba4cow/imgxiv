package com.kaba4cow.imgxiv.image.storage;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.common.exception.ImageUploadException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class ImageStorageLoggingAspect {

	@Around("execution(* ImageStorage.saveImage(..)) && args(storageKey, file)")
	public Object aroundSaveImage(ProceedingJoinPoint joinPoint, String storageKey, MultipartFile file) throws Throwable {
		try {
			Object result = joinPoint.proceed();
			logSaved(storageKey, file);
			return result;
		} catch (Exception exception) {
			logFailedToSave(storageKey, file, exception);
			throw new ImageUploadException("Failed to save image", exception);
		}
	}

	private void logSaved(String storageKey, MultipartFile file) {
		log.info("Saved image: {} (filename={}, size={}, contentType={})", //
				storageKey, //
				file.getOriginalFilename(), //
				file.getSize(), //
				file.getContentType()//
		);
	}

	private void logFailedToSave(String storageKey, MultipartFile file, Exception exception) {
		log.error("Failed to save image: {} (filename={}, size={}, contentType={}). Cause: {}", //
				storageKey, //
				file.getOriginalFilename(), //
				file.getSize(), //
				file.getContentType(), //
				exception.getMessage(), //
				exception//
		);
	}

	@AfterReturning(pointcut = "execution(* ImageStorage.deleteImage(..)) && args(storageKey)", argNames = "storageKey")
	public void afterDeleteImage(String storageKey) {
		log.info("Deleted image: {}", storageKey);
	}

	@After("execution(* ImageStorage.clearStorage())")
	public void afterClearStorage() {
		log.info("Cleared image storage");
	}

}
