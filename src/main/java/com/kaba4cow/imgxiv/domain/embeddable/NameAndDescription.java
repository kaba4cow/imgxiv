package com.kaba4cow.imgxiv.domain.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
@ToString
@Embeddable
public class NameAndDescription {

	@Column(name = "column_name", length = 32)
	private String name;

	@Column(name = "column_description", length = 1024)
	private String description;

}
