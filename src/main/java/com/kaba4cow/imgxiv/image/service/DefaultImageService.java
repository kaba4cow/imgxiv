package com.kaba4cow.imgxiv.image.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.domain.embeddable.PostImage;
import com.kaba4cow.imgxiv.image.ImageResource;
import com.kaba4cow.imgxiv.image.storage.ImageStorage;
import com.kaba4cow.imgxiv.image.storage.StorageKeyGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultImageService implements ImageService {

	private final ImageStorage imageStorage;

	private final StorageKeyGenerator storageKeyGenerator;

	@Override
	public PostImage createImage(MultipartFile file) {
		String storageKey = storageKeyGenerator.generateKey(file.getOriginalFilename());
		imageStorage.uploadImage(storageKey, file);
		return PostImage.builder()//
				.fileName(file.getOriginalFilename())//
				.fileSize(file.getSize())//
				.storageKey(storageKey)//
				.contentType(file.getContentType())//
				.build();
	}

	@Override
	public ImageResource getImage(PostImage postImage) {
		return new ImageResource(//
				imageStorage.getImage(postImage.getStorageKey()), //
				postImage.getFileSize(), //
				postImage.getContentType()//
		);
	}

	@Override
	public void deleteImage(PostImage postImage) {
		imageStorage.deleteImage(postImage.getStorageKey());
	}

}
