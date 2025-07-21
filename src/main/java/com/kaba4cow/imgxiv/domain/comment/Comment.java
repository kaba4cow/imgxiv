package com.kaba4cow.imgxiv.domain.comment;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.kaba4cow.imgxiv.domain.base.EntityWithId;
import com.kaba4cow.imgxiv.domain.embeddable.CreatedAt;
import com.kaba4cow.imgxiv.domain.embeddable.UpdatedAt;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity
@ToString
@Table(name = "table_comment")
public class Comment extends EntityWithId {

	@ManyToOne(optional = false)
	@JoinColumn(name = "column_post_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Post post;

	@ManyToOne(optional = false)
	@JoinColumn(name = "column_author_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User author;

	@Column(name = "column_text", length = 1024)
	private String text;

	@Embedded
	private CreatedAt createdAt = new CreatedAt();

	@Embedded
	private UpdatedAt updatedAt = new UpdatedAt();

}
