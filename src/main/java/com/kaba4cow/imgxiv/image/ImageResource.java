package com.kaba4cow.imgxiv.image;

import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;

public class ImageResource extends InputStreamResource {

	private final long contentLength;

	private final MediaType contentType;

	public ImageResource(InputStream inputStream, long contentLength, String contentType) {
		super(inputStream);
		this.contentLength = contentLength;
		this.contentType = MediaType.parseMediaType(contentType);
	}

	@Override
	public long contentLength() {
		return contentLength;
	}

	public MediaType contentType() {
		return contentType;
	}

}
