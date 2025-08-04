package com.kaba4cow.imgxiv.domain.comment.dto;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.comment.model.Comment;

@Component
public class CommentMapper {

	public CommentDto mapToDto(Comment comment) {
		return new CommentDto(//
				comment.getId(), //
				comment.getPost().getId(), //
				comment.getAuthor().getId(), //
				comment.getText(), //
				comment.getCreatedAt().getTimestamp(), //
				comment.getUpdatedAt().getTimestamp()//
		);
	}

}
