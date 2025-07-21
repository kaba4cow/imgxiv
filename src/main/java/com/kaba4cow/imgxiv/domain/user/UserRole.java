package com.kaba4cow.imgxiv.domain.user;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {

	USER("user", Set.of(//
			"create-post"//
	)), //
	MODERATOR("moderator", Set.of(//
			"create-category", //

			"create-tag", //

			"edit-post-na", //
			"delete-post-na", //

			"delete-comment-na" //
	)), //
	ADMIN("admin", Set.of(//
			"assign-moderator"//
	));

	private final Set<? extends GrantedAuthority> authorities;

	private UserRole(String roleName, Set<String> permissions) {
		this.authorities = getAuthorities(roleName, permissions).stream()//
				.map(SimpleGrantedAuthority::new)//
				.collect(Collectors.toUnmodifiableSet());
	}

	private static Set<String> getAuthorities(String roleName, Set<String> permissions) {
		Set<String> authorities = new HashSet<>();
		authorities.add("ROLE_".concat(roleName));
		authorities.addAll(permissions);
		return authorities;
	}

	public Set<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

}
