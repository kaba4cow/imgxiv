package com.kaba4cow.imgxiv.domain.tag.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;
import com.kaba4cow.imgxiv.domain.tag.service.TagService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CategoryTagController implements CategoryTagControllerApiDoc {

	private final TagService tagService;

	@Override
	public ResponseEntity<List<TagDto>> getCategorizedTags(Long category) {
		return ResponseEntity.ok(tagService.getTagsByCategory(category));
	}

	@Override
	public ResponseEntity<List<TagDto>> getUncategorizedTags() {
		return ResponseEntity.ok(tagService.getTagsByDefaultCategory());
	}

}
