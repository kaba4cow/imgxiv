package com.kaba4cow.imgxiv.domain.user;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {

	USER(UserRoles.USER, Set.of(//
			UserAuthorities.CREATE_POST//
	)), //
	MODERATOR(UserRoles.MODERATOR, Set.of(//
			UserAuthorities.CREATE_CATEGORY, //

			UserAuthorities.EDIT_POST_NA, //
			UserAuthorities.DELETE_POST_NA, //

			UserAuthorities.DELETE_COMMENT_NA, //

			UserAuthorities.MANAGE_TAGS, //
			UserAuthorities.MANAGE_CATEGORIES, //

			UserAuthorities.VIEW_MODERATORS//
	)), //
	ADMIN(UserRoles.ADMIN, Set.of(//
			UserAuthorities.MANAGE_MODERATORS, //
			UserAuthorities.VIEW_MODERATORS//
	));

	private final Set<? extends GrantedAuthority> authorities;

	private UserRole(String roleName, Set<String> permissions) {
		this.authorities = getAuthorities(roleName, permissions).stream()//
				.map(SimpleGrantedAuthority::new)//
				.collect(Collectors.toUnmodifiableSet());
	}

	public static UserRole defaultRole() {
		return USER;
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
