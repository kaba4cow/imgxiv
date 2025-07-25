package com.kaba4cow.imgxiv.domain.post.dto;

import org.springframework.web.multipart.MultipartFile;

import com.kaba4cow.imgxiv.common.validation.ContentType;
import com.kaba4cow.imgxiv.common.validation.FileSize;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Request for creating a new post")
public class PostCreateRequest extends PostTagsRequest {

	@FileSize(max = 16L * 1024L * 1024L, message = "File size must not exceed 16MB")
	@ContentType(allowed = { "image/jpeg", "image/png" }, message = "Unsupported content type")
	@Schema(//
			description = "Image file" //
	)
	private MultipartFile image;

}
