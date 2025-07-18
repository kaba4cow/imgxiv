package com.kaba4cow.imgxiv.domain.post.dto;

import com.kaba4cow.imgxiv.common.dto.PageableRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Request for querying posts")
public class PostQueryRequest extends PageableRequest {

	@Size(max = 1024, message = "Query is too long (max 1024 characters)")
	@Schema(//
			description = "Query for matching posts", //
			example = "cat pig !dog cute" //
	)
	private String query = "";

}
