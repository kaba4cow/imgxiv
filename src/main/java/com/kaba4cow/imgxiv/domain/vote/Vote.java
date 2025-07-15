package com.kaba4cow.imgxiv.domain.vote;

import com.kaba4cow.imgxiv.domain.embeddable.PostAndUser;
import com.kaba4cow.imgxiv.domain.superclass.EntityWithId;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "table_vote")
public class Vote extends EntityWithId {

	@Embedded
	private PostAndUser postAndUser = new PostAndUser();

	@Enumerated(EnumType.STRING)
	@Column(name = "column_type")
	private VoteType type;

}
