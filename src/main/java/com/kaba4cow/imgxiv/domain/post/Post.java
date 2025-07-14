package com.kaba4cow.imgxiv.domain.post;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "table_post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "column_id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "column_author_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User author;

	@ManyToMany
	@JoinTable(name = "table_post_tag")
	private Set<Tag> tags = new TreeSet<>();

	@CreationTimestamp
	@Column(name = "column_created_at", updatable = false)
	private LocalDateTime createdAt;

}
