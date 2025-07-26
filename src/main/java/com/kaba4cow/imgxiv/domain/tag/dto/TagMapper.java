package com.kaba4cow.imgxiv.domain.tag.dto;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.tag.Tag;

@Component
public class TagMapper {

	public TagDto mapToDto(Tag tag) {
		return new TagDto(//
				tag.getId(), //
				tag.getName(), //
				tag.getDescription(), //
				tag.getCategory().getId()//
		);
	}

}
