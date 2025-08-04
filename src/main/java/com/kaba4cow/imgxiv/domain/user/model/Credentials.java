package com.kaba4cow.imgxiv.domain.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
@Embeddable
public class Credentials {

	@Column(name = "column_username", length = 32, nullable = false)
	private String username;

	@Column(name = "column_email", length = 64, nullable = false)
	private String email;

	@ToString.Exclude
	@Column(name = "column_password_hash", nullable = false)
	private String passwordHash;

}
