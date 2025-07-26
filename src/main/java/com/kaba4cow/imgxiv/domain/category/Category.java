package com.kaba4cow.imgxiv.domain.category;

import com.kaba4cow.imgxiv.domain.base.EntityWithId;

import jakarta.persistence.Column;
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

	@Column(name = "column_name", nullable = false, length = 32)
	private String name;

	@Column(name = "column_description", nullable = false, length = 1024)
	private String description;

}
