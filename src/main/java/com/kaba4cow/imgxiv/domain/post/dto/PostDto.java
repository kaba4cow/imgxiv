package com.kaba4cow.imgxiv.domain.post.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostDto {

	private final Long id;

	private final Long authorId;

	private final Set<Long> tagIds;

	private final LocalDateTime createdAt;

	private final LocalDateTime updatedAt;

}
