package com.kaba4cow.imgxiv.domain.embeddable;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.user.User;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
@Embeddable
public class PostAndUser {

	@ManyToOne
	@JoinColumn(name = "column_post_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Post post;

	@ManyToOne
	@JoinColumn(name = "column_user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

}
