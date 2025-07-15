package com.kaba4cow.imgxiv.domain.user;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.kaba4cow.imgxiv.domain.EntityWithId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "table_user", uniqueConstraints = { //
		@UniqueConstraint(columnNames = "column_username"), //
		@UniqueConstraint(columnNames = "column_email") //
})
public class User extends EntityWithId {

	@Column(name = "column_username", length = 32, nullable = false)
	private String username;

	@Column(name = "column_email", length = 64, nullable = false)
	private String email;

	@Column(name = "column_password_hash", nullable = false)
	private String passwordHash;

	@Enumerated(EnumType.STRING)
	@Column(name = "column_role", nullable = false)
	private UserRole role;

	@CreationTimestamp
	@Column(name = "column_created_at", updatable = false)
	private LocalDateTime createdAt;

}
