package com.kaba4cow.imgxiv.domain.post;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.kaba4cow.imgxiv.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "table_comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "column_id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "column_author_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User author;

	@CreationTimestamp
	@Column(name = "column_created_at", updatable = false)
	private LocalDateTime createdAt;

}
