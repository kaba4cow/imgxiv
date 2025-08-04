package com.kaba4cow.imgxiv.domain.vote.model;

import java.io.Serializable;

import com.kaba4cow.imgxiv.domain.post.model.Post;
import com.kaba4cow.imgxiv.domain.user.model.User;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Embeddable
public class VoteId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long postId;

	private Long userId;

	public static VoteId of(Post post, User user) {
		return of(post.getId(), user.getId());
	}

}
