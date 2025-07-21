package com.kaba4cow.imgxiv.domain.comment;

import com.kaba4cow.imgxiv.domain.base.EntityWithId;
import com.kaba4cow.imgxiv.domain.embeddable.CreatedAt;
import com.kaba4cow.imgxiv.domain.embeddable.PostAndUser;
import com.kaba4cow.imgxiv.domain.embeddable.UpdatedAt;

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
public class Comment extends EntityWithId {

	@Embedded
	private PostAndUser postAndUser = new PostAndUser();

	@Column(name = "column_text", length = 1024)
	private String text;

	@Embedded
	private CreatedAt createdAt = new CreatedAt();

	@Embedded
	private UpdatedAt updatedAt = new UpdatedAt();

}
