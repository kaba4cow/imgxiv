package com.kaba4cow.imgxiv.domain.link.posttag.model;

import com.kaba4cow.imgxiv.domain.post.model.Post;
import com.kaba4cow.imgxiv.domain.tag.model.Tag;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "table_post_tag")
public class PostTag {

	@EqualsAndHashCode.Include
	@EmbeddedId
	private PostTagId id;

	@MapsId("postId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "column_post_id")
	private Post post;

	@MapsId("tagId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "column_tag_id")
	private Tag tag;

	public static PostTag of(Post post, Tag tag) {
		PostTag postTag = new PostTag();
		postTag.setId(PostTagId.of(post, tag));
		postTag.setPost(post);
		postTag.setTag(tag);
		return postTag;
	}

}
