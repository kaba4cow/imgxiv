package com.kaba4cow.imgxiv.domain.tag;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.embeddable.NameAndDescription;
import com.kaba4cow.imgxiv.domain.superclass.EntityWithId;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "table_tag", uniqueConstraints = @UniqueConstraint(columnNames = "column_name"))
public class Tag extends EntityWithId implements Comparable<Tag> {

	@Embedded
	private NameAndDescription nameAndDescription = new NameAndDescription();

	@ManyToOne
	@JoinColumn(name = "column_category_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Category category;

	@Override
	public int compareTo(Tag other) {
		return this.nameAndDescription.getName().compareTo(other.nameAndDescription.getName());
	}

}
