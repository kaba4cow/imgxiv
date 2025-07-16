package com.kaba4cow.imgxiv.auth.service;

import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthenticatedUserService {

	public User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!hasAuthenticatedUser(authentication))
			throw new IllegalStateException("No authenticated user found");
		else {
			UserDetailsAdapter userDetails = (UserDetailsAdapter) authentication.getPrincipal();
			return userDetails.getUser();
		}
	}

	private boolean hasAuthenticatedUser(Authentication authentication) {
		return Objects.nonNull(authentication) && authentication.getPrincipal() instanceof UserDetailsAdapter;
	}

}
