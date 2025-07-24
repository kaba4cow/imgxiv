package com.kaba4cow.imgxiv.common.dto;

import org.springframework.data.domain.Sort;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class PaginationParams {

	@Min(value = 0, message = "Page must be 0 or greater")
	@Schema(//
			description = "Page number, zero-based", //
			example = "0" //
	)
	private int page = 0;

	@Min(value = 1, message = "Page size must be at least 1")
	@Max(value = 100, message = "Page size must not exceed 100")
	@Schema(//
			description = "Number of posts per page", //
			example = "20" //
	)
	private int size = 20;

	@Schema(//
			description = "Sort direction", //
			example = "DESC" //
	)
	private Sort.Direction sort = Sort.Direction.DESC;

}
