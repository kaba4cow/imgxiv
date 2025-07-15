package com.kaba4cow.imgxiv.domain.comment;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.kaba4cow.imgxiv.domain.AbstractEntity;
import com.kaba4cow.imgxiv.domain.embeddable.PostAndUser;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
public class Comment extends AbstractEntity {

	@Embedded
	private PostAndUser postAndUser = new PostAndUser();

	@Column(name = "column_text", length = 1024)
	private String text;

	@CreationTimestamp
	@Column(name = "column_created_at", updatable = false)
	private LocalDateTime createdAt;

}
