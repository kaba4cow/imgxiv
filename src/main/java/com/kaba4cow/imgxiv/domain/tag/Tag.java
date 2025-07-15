package com.kaba4cow.imgxiv.domain.tag;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.embeddable.NameAndDescription;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "table_tag", uniqueConstraints = @UniqueConstraint(columnNames = "column_name"))
public class Tag implements Comparable<Tag> {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

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
