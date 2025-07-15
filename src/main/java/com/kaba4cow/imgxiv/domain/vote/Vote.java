package com.kaba4cow.imgxiv.domain.vote;

import java.util.UUID;

import com.kaba4cow.imgxiv.domain.embeddable.PostAndUser;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Embedded
	private PostAndUser postAndUser = new PostAndUser();

	@Enumerated(EnumType.STRING)
	@Column(name = "column_type")
	private VoteType type;

}
