package com.kaba4cow.imgxiv.domain.user;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {

	USER(UserAuthority.USER), //
	MODERATOR(UserAuthority.MODERATOR), //
	ADMIN(UserAuthority.ADMIN);

	private final Set<? extends GrantedAuthority> grantedAuthorities;

	private UserRole(UserAuthority... grantedAuthorities) {
		this.grantedAuthorities = Arrays.stream(grantedAuthorities)//
				.map(UserAuthority::name)//
				.map("ROLE_"::concat)//
				.map(SimpleGrantedAuthority::new)//
				.collect(Collectors.toSet());
	}

	public Set<? extends GrantedAuthority> getGrantedAuthorities() {
		return grantedAuthorities;
	}

}
