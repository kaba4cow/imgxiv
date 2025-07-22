package com.kaba4cow.imgxiv.common.dto.parameter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoryIdParams {

	@NotNull(message = "Category ID is required") //
	@Schema(//
			description = "ID of the category", //
			example = "1"//
	) //
	private Long id;

}
