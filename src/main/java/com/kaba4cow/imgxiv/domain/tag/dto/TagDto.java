package com.kaba4cow.imgxiv.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TagDto {

	private Long id;

	private String name;

	private String description;

	private Long categoryId;

}
