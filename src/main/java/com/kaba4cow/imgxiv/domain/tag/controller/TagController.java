package com.kaba4cow.imgxiv.domain.tag.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.common.dto.CategoryIdRequest;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;
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
	public ResponseEntity<List<TagDto>> getCategorizedTags(CategoryIdRequest request) {
		return ResponseEntity.ok(tagService.getTagsByCategory(request.getCategoryId()));
	}

	@Override
	public ResponseEntity<List<TagDto>> getUncategorizedTags() {
		return ResponseEntity.ok(tagService.getTagsByDefaultCategory());
	}

}
