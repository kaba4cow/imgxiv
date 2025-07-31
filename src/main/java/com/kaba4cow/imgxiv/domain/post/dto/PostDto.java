package com.kaba4cow.imgxiv.domain.post.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto {

	private Long id;

	private Long authorId;

	private Set<String> tagNames;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
