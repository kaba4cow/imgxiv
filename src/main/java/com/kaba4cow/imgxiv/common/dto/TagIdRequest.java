package com.kaba4cow.imgxiv.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TagIdRequest {

	@NotNull(message = "Tag ID is required") //
	@Schema(//
			description = "ID of the tag", //
			example = "1"//
	) //
	private Long tagId;

}
