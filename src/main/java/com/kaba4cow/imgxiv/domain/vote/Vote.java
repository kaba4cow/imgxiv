package com.kaba4cow.imgxiv.domain.vote;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "table_vote")
public class Vote {

	@EmbeddedId
	private VoteId id;

	@MapsId("postId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "column_post_id")
	private Post post;

	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "column_user_id")
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(name = "column_type", nullable = false)
	private VoteType type;

}
