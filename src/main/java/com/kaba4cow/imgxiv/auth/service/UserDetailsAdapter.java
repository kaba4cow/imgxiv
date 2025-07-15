package com.kaba4cow.imgxiv.auth.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.role.UserAuthorityRegistry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class UserDetailsAdapter implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final User user;

	private final UserAuthorityRegistry userAuthorityRegistry;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userAuthorityRegistry.getAuthorities(user.getRole());
	}

	@Override
	public String getPassword() {
		return user.getPasswordHash();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

}
