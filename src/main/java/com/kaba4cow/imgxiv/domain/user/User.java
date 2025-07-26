package com.kaba4cow.imgxiv.domain.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kaba4cow.imgxiv.domain.base.EntityWithId;
import com.kaba4cow.imgxiv.domain.embeddable.CreatedAt;

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
public class User extends EntityWithId implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Embedded
	private Credentials credentials = new Credentials();

	@Enumerated(EnumType.STRING)
	@Column(name = "column_role", nullable = false)
	private UserRole role;

	@Embedded
	private CreatedAt createdAt = new CreatedAt();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}

	@Override
	public String getPassword() {
		return getCredentials().getPasswordHash();
	}

	@Override
	public String getUsername() {
		return getId().toString();
	}

}
