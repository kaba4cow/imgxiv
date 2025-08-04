package com.kaba4cow.imgxiv.domain.post.dto;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.post.model.Post;
import com.kaba4cow.imgxiv.domain.tag.model.Tag;

@Component
public class PostMapper {

	public PostDto mapToDto(Post post) {
		return new PostDto(//
				post.getId(), //
				post.getAuthor().getId(), //
				post.getTags().stream()//
						.map(Tag::getName)//
						.collect(Collectors.toSet()), //
				post.getCreatedAt().getTimestamp(), //
				post.getUpdatedAt().getTimestamp()//
		);
	}

}
