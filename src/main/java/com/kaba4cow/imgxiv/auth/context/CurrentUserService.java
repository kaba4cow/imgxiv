package com.kaba4cow.imgxiv.auth.context;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.auth.userdetails.UserDetailsAdapter;
import com.kaba4cow.imgxiv.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CurrentUserService {

	public UserDetailsAdapter getDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!hasAuthenticatedUser(authentication))
			throw new IllegalStateException("No authenticated user found");
		else
			return (UserDetailsAdapter) authentication.getPrincipal();
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getDetails().getAuthorities();
	}

	public User getUser() {
		return getDetails().getUser();
	}

	private boolean hasAuthenticatedUser(Authentication authentication) {
		return Objects.nonNull(authentication) && authentication.getPrincipal() instanceof UserDetailsAdapter;
	}

}
