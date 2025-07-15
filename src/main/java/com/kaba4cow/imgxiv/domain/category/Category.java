package com.kaba4cow.imgxiv.domain.category;

import java.util.UUID;

import com.kaba4cow.imgxiv.domain.embeddable.NameAndDescription;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "table_category", uniqueConstraints = @UniqueConstraint(columnNames = "column_name"))
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Embedded
	private NameAndDescription nameAndDescription = new NameAndDescription();

}
