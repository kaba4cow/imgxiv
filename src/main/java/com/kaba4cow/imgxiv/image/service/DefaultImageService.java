package com.kaba4cow.imgxiv.image.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.domain.embeddable.PostImage;
import com.kaba4cow.imgxiv.image.ImageResource;
import com.kaba4cow.imgxiv.image.storage.ImageStorage;
import com.kaba4cow.imgxiv.image.thumbnail.ThumbnailGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultImageService implements ImageService {

	private static final String IMAGE_PATH = "image/";
	private static final String THUMBNAIL_PATH = "thumb/";

	private final ImageStorage imageStorage;

	private final ThumbnailGenerator thumbnailGenerator;

	private final StorageKeyGenerator storageKeyGenerator;

	@Override
	public PostImage createImages(MultipartFile file) {
		String storageKey = storageKeyGenerator.generateKey(file.getOriginalFilename());
		saveImages(storageKey, file);
		return PostImage.builder()//
				.fileName(file.getOriginalFilename())//
				.fileSize(file.getSize())//
				.storageKey(storageKey)//
				.contentType(file.getContentType())//
				.build();
	}

	private void saveImages(String storageKey, MultipartFile file) {
		log.info("Uploading images: {}", storageKey);
		imageStorage.saveImage(getFullStorageKey(IMAGE_PATH, storageKey), file);
		imageStorage.saveImage(getFullStorageKey(THUMBNAIL_PATH, storageKey), thumbnailGenerator.generateThumbnail(file));
	}

	@Override
	public ImageResource getImage(PostImage postImage) {
		return getImage(IMAGE_PATH, postImage);
	}

	@Override
	public ImageResource getThumbnail(PostImage postImage) {
		return getImage(THUMBNAIL_PATH, postImage);
	}

	private ImageResource getImage(String imagePath, PostImage postImage) {
		return new ImageResource(//
				imageStorage.getImage(getFullStorageKey(imagePath, postImage)), //
				postImage.getFileSize(), //
				postImage.getContentType()//
		);
	}

	@Override
	public void deleteImages(PostImage postImage) {
		log.info("Deleting images: {}", postImage.getStorageKey());
		imageStorage.deleteImage(getFullStorageKey(IMAGE_PATH, postImage));
		imageStorage.deleteImage(getFullStorageKey(THUMBNAIL_PATH, postImage));
	}

	private String getFullStorageKey(String imagePath, PostImage postImage) {
		return getFullStorageKey(imagePath, postImage.getStorageKey());
	}

	private String getFullStorageKey(String imagePath, String storageKey) {
		return imagePath.concat(storageKey);
	}

}
