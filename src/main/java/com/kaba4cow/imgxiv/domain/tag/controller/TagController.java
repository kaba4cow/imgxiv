package com.kaba4cow.imgxiv.domain.tag.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.common.dto.parameter.CategoryIdRequest;
import com.kaba4cow.imgxiv.domain.tag.dto.TagCreateRequest;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;
import com.kaba4cow.imgxiv.domain.tag.service.TagService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TagController implements TagControllerApiDoc {

	private final TagService tagService;

	@Override
	public ResponseEntity<TagDto> createTag(TagCreateRequest request) {
		return ResponseEntity.ok(tagService.create(request));
	}

	@Override
	public ResponseEntity<List<TagDto>> getAllTags() {
		return ResponseEntity.ok(tagService.findAll());
	}

	@Override
	public ResponseEntity<List<TagDto>> getTagsByCategory(CategoryIdRequest request) {
		return ResponseEntity.ok(tagService.findByCategoryId(request.getCategoryId()));
	}

}
