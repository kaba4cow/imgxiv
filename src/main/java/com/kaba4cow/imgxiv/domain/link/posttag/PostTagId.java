package com.kaba4cow.imgxiv.domain.link.posttag;

import java.io.Serializable;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.tag.Tag;

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
public class PostTagId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long postId;

	private Long tagId;

	public static PostTagId of(Post post, Tag tag) {
		return of(post.getId(), tag.getId());
	}

}
