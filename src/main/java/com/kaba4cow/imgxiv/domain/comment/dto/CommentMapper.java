package com.kaba4cow.imgxiv.domain.comment.dto;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.comment.Comment;

@Component
public class CommentMapper {

	public CommentDto mapToDto(Comment comment) {
		return new CommentDto(//
				comment.getId(), //
				comment.getPostAndUser().getPost().getId(), //
				comment.getPostAndUser().getUser().getId(), //
				comment.getText(), //
				comment.getCreatedAt().getTimestamp()//
		);
	}

}
