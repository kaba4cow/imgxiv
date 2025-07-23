package com.kaba4cow.imgxiv.image.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.domain.embeddable.PostImage;
import com.kaba4cow.imgxiv.image.ImageResource;
import com.kaba4cow.imgxiv.image.storage.ImageStorage;
import com.kaba4cow.imgxiv.image.storage.StorageKeyGenerator;
import com.kaba4cow.imgxiv.image.thumbnail.ThumbnailGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultImageService implements ImageService {

	private static final String IMAGE_PATH = "image/";
	private static final String THUMBNAIL_PATH = "thumb/";

	private final ImageStorage imageStorage;

	private final ThumbnailGenerator thumbnailGenerator;

	private final StorageKeyGenerator storageKeyGenerator;

	@Override
	public PostImage createImage(MultipartFile file) {
		String storageKey = storageKeyGenerator.generateKey(file.getOriginalFilename());
		uploadImages(storageKey, file);
		return PostImage.builder()//
				.fileName(file.getOriginalFilename())//
				.fileSize(file.getSize())//
				.storageKey(storageKey)//
				.contentType(file.getContentType())//
				.build();
	}

	private void uploadImages(String storageKey, MultipartFile file) {
		imageStorage.uploadImage(getFullStorageKey(IMAGE_PATH, storageKey), file);
		imageStorage.uploadImage(getFullStorageKey(THUMBNAIL_PATH, storageKey), thumbnailGenerator.generateThumbnail(file));
	}

	@Override
	public ImageResource getFullImage(PostImage postImage) {
		return getImage(IMAGE_PATH, postImage);
	}

	@Override
	public ImageResource getThumbnailImage(PostImage postImage) {
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
