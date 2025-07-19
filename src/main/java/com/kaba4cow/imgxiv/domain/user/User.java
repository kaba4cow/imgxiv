package com.kaba4cow.imgxiv.domain.user;

import com.kaba4cow.imgxiv.domain.base.EntityWithId;
import com.kaba4cow.imgxiv.domain.embeddable.CreatedAt;
import com.kaba4cow.imgxiv.domain.embeddable.Credentials;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

	@Embedded
	private Credentials credentials = new Credentials();

	@Enumerated(EnumType.STRING)
	@Column(name = "column_role", nullable = false)
	private UserRole role;

	@Embedded
	private CreatedAt createdAt = new CreatedAt();

}
