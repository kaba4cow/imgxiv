package com.kaba4cow.imgxiv.domain.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.kaba4cow.imgxiv.domain.base.EntityWithId;
import com.kaba4cow.imgxiv.domain.embeddable.CreatedAt;
import com.kaba4cow.imgxiv.domain.posttag.PostTag;
import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
@Table(name = "table_post")
public class Post extends EntityWithId {

	@ManyToOne(optional = false)
	@JoinColumn(name = "column_author_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User author;

	@ToString.Exclude
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PostTag> postTags = new ArrayList<>();

	@Embedded
	private CreatedAt createdAt = new CreatedAt();

	public Set<Tag> getTags() {
		return postTags.stream()//
				.map(PostTag::getTag)//
				.collect(Collectors.toSet());
	}

	public boolean hasTag(Tag tag) {
		return postTags.contains(postTagOf(tag));
	}

	public void addTag(Tag tag) {
		PostTag postTag = postTagOf(tag);
		if (!postTags.contains(postTag))
			postTags.add(postTag);
	}

	public void removeTag(Tag tag) {
		postTags.remove(postTagOf(tag));
	}

	private PostTag postTagOf(Tag tag) {
		return PostTag.of(this, tag);
	}

}
