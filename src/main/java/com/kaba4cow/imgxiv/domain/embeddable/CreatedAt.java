package com.kaba4cow.imgxiv.domain.embeddable;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
public class CreatedAt {

	@CreationTimestamp
	@Column(name = "column_created_at", updatable = false)
	private LocalDateTime timestamp;

}
