package com.kaba4cow.imgxiv.domain.post;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.kaba4cow.imgxiv.domain.base.EntityWithId;
import com.kaba4cow.imgxiv.domain.embeddable.CreatedAt;
import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.user.User;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@ToString
@Entity
@Table(name = "table_post")
public class Post extends EntityWithId {

	@ManyToOne(optional = false)
	@JoinColumn(name = "column_author_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User author;

	@ManyToMany
	@JoinTable(name = "table_post_tag")
	private Set<Tag> tags = new HashSet<>();

	@Embedded
	private CreatedAt createdAt = new CreatedAt();

}
