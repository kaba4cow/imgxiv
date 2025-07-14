package com.kaba4cow.imgxiv.domain.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
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
@Table(name = "table_user", uniqueConstraints = { //
		@UniqueConstraint(columnNames = "column_username"), //
		@UniqueConstraint(columnNames = "column_email") //
})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "column_id")
	private UUID id;

	@Column(name = "column_username", nullable = false)
	private String username;

	@Column(name = "column_email", nullable = false)
	private String email;

	@Column(name = "column_password_hash", nullable = false)
	private String passwordHash;

	@CreationTimestamp
	@Column(name = "column_created_at", updatable = false)
	private LocalDateTime createdAt;

}
