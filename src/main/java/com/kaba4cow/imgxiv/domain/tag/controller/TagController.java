package com.kaba4cow.imgxiv.domain.tag.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;
import com.kaba4cow.imgxiv.domain.tag.dto.TagEditRequest;
import com.kaba4cow.imgxiv.domain.tag.service.TagService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TagController implements TagControllerApiDoc {

	private final TagService tagService;

	@Override
	public ResponseEntity<TagDto> getTag(Long id) {
		return ResponseEntity.ok(tagService.getTag(id));
	}

	@Override
	public ResponseEntity<TagDto> editTag(Long id, TagEditRequest request) {
		throw new UnsupportedOperationException();
	}

}
