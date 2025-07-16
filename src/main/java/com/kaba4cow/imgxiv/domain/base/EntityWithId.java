package com.kaba4cow.imgxiv.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class EntityWithId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "column_id")
	private Long id;

}
