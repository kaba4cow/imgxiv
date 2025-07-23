package com.kaba4cow.imgxiv.setup;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.common.condition.ConditionalOnPropertyEnabled;
import com.kaba4cow.imgxiv.image.storage.ImageStorage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnPropertyEnabled(prefix = "setup.image-storage-cleaner")
@Component
public class ImageStorageCleaner implements ApplicationRunner {

	private final ImageStorage imageStorage;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {
			log.info("Clearing image storage...");
			imageStorage.clearStorage();
			log.info("Image storage cleanup successful");
		} catch (Exception exception) {
			log.error("Image storage cleanup failed. Cause: {}", exception.getMessage(), exception);
		}
	}

}
