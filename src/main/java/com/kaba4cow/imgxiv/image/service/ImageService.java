package com.kaba4cow.imgxiv.image.service;

import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.domain.embeddable.PostImage;
import com.kaba4cow.imgxiv.image.ImageResource;

public interface ImageService {

	PostImage createImages(MultipartFile file);

	ImageResource getImage(PostImage postImage);

	ImageResource getThumbnail(PostImage postImage);

	void deleteImages(PostImage postImage);

}
