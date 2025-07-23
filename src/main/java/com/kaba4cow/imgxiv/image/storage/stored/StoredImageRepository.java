package com.kaba4cow.imgxiv.image.storage.stored;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;

public interface StoredImageRepository extends JpaRepository<StoredImage, String> {

	default StoredImage findByIdOrThrow(String id) {
		return findById(id).orElseThrow(() -> new NotFoundException("Image", id));
	}

}
