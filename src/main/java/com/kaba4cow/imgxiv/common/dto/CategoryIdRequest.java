package com.kaba4cow.imgxiv.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryIdRequest {

	@NotNull(message = "Category ID is required") //
	@Schema(//
			description = "ID of the category", //
			example = "1"//
	) //
	private Long categoryId;

}
