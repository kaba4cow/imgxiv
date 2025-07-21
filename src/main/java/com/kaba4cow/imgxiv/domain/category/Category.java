package com.kaba4cow.imgxiv.domain.category;

import com.kaba4cow.imgxiv.domain.base.EntityWithId;
import com.kaba4cow.imgxiv.domain.embeddable.NameAndDescription;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "table_category", uniqueConstraints = @UniqueConstraint(columnNames = "column_name"))
public class Category extends EntityWithId {

	@Builder.Default
	@Embedded
	private NameAndDescription nameAndDescription = new NameAndDescription();

}
