package com.kaba4cow.imgxiv.domain.vote;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "table_vote")
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "column_id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "column_post_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Post post;

	@ManyToOne
	@JoinColumn(name = "column_voter_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User voter;

	@Enumerated(EnumType.STRING)
	@Column(name = "column_type")
	private VoteType type;

}
