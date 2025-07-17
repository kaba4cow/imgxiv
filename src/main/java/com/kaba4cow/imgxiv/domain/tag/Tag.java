package com.kaba4cow.imgxiv.domain.tag;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.kaba4cow.imgxiv.domain.base.EntityWithId;
import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.embeddable.NameAndDescription;
import com.kaba4cow.imgxiv.domain.posttag.PostTag;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Tag extends EntityWithId {

	@Embedded
	private NameAndDescription nameAndDescription = new NameAndDescription();

	@ToString.Exclude
	@OneToMany(mappedBy = "tag")
	private List<PostTag> postTags = new ArrayList<>();

	@ManyToOne(optional = false)
	@JoinColumn(name = "column_category_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Category category;

}
