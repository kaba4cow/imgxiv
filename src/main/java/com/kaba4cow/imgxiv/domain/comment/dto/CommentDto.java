package com.kaba4cow.imgxiv.domain.comment.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CommentDto {

	private final Long id;

	private final Long postId;

	private final Long userId;

	private final String text;

	private final LocalDateTime createdAt;

	private final LocalDateTime updatedAt;

}
