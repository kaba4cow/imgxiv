package com.kaba4cow.imgxiv.image.thumbnail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@RequiredArgsConstructor
@Service
public class DefaultThumbnailGenerator implements ThumbnailGenerator {

	private final ThumbnailProperties thumbnailProperties;

	@Override
	public MultipartFile generateThumbnail(MultipartFile file) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			createThumbnail(file.getInputStream(), outputStream);
			return ThumbnailMultipartFile.builder()//
					.bytes(outputStream.toByteArray())//
					.name(file.getName())//
					.originalFilename(file.getOriginalFilename())//
					.contentType(thumbnailProperties.getContentType())//
					.build();
		} catch (Exception exception) {
			throw new RuntimeException("Failed to generate thumbnail image");
		}
	}

	private void createThumbnail(InputStream input, OutputStream output) throws IOException {
		Thumbnails.of(input)//
				.size(thumbnailProperties.getImageWidth(), thumbnailProperties.getImageHeight())//
				.outputFormat(thumbnailProperties.getImageFormat())//
				.toOutputStream(output);
	}

}
