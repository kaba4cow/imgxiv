package com.kaba4cow.imgxiv.domain.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class NameAndDescription {

	@Column(name = "column_name")
	private String name;

	@Column(name = "column_description")
	private String description;

}
