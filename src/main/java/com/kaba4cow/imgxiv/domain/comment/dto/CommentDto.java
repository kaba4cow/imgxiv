package com.kaba4cow.imgxiv.domain.comment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDto {

	private Long id;

	private Long postId;

	private Long userId;

	private String text;

	private LocalDateTime createdAt;

}
