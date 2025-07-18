package com.kaba4cow.imgxiv.common.dto;

import org.springframework.data.domain.Sort;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Subrequest for specifying sorting information")
public class SortingRequest {

	@Schema(//
			description = "Sort direction", //
			example = "DESC" //
	)
	private Sort.Direction direction = Sort.Direction.DESC;

}
