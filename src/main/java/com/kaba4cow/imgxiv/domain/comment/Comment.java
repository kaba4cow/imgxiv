package com.kaba4cow.imgxiv.domain.comment;

import com.kaba4cow.imgxiv.domain.embeddable.PostAndUser;
import com.kaba4cow.imgxiv.domain.superclass.EntityWithIdAndCreationTimestamp;

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
public class Comment extends EntityWithIdAndCreationTimestamp {

	@Embedded
	private PostAndUser postAndUser = new PostAndUser();

	@Column(name = "column_text", length = 1024)
	private String text;

}
