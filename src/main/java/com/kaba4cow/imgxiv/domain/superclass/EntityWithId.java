package com.kaba4cow.imgxiv.domain.superclass;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class EntityWithId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "column_id")
	private Long id;

}
