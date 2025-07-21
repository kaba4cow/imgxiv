package com.kaba4cow.imgxiv.domain.embeddable;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

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
public class UpdatedAt {

	@UpdateTimestamp
	@Column(name = "column_updated_at")
	private LocalDateTime timestamp;

}
