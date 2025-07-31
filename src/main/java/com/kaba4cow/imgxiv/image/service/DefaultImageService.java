package com.kaba4cow.imgxiv.image.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.domain.post.PostImage;
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
		MultipartFile thumbnailFile = thumbnailGenerator.generateThumbnail(file);
		String storageKey = storageKeyGenerator.generateKey(file.getOriginalFilename());
		log.info("Uploading images: {}", storageKey);
		imageStorage.saveImage(getFullStorageKey(IMAGE_PATH, storageKey), file);
		imageStorage.saveImage(getFullStorageKey(THUMBNAIL_PATH, storageKey), thumbnailFile);
		return PostImage.builder()//
				.storageKey(storageKey)//
				.fileName(file.getOriginalFilename())//
				.fileSize(file.getSize())//
				.contentType(file.getContentType())//
				.thumbnailFileSize(thumbnailFile.getSize())//
				.thumbnailContentType(thumbnailFile.getContentType())//
				.build();
	}

	@Override
	public ImageResource getImage(PostImage postImage) {
		return new ImageResource(//
				imageStorage.getImage(getFullStorageKey(IMAGE_PATH, postImage)), //
				postImage.getFileSize(), //
				postImage.getContentType()//
		);
	}

	@Override
	public ImageResource getThumbnail(PostImage postImage) {
		return new ImageResource(//
				imageStorage.getImage(getFullStorageKey(THUMBNAIL_PATH, postImage)), //
				postImage.getThumbnailFileSize(), //
				postImage.getThumbnailContentType()//
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
