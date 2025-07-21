package com.kaba4cow.imgxiv.auth.context;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CurrentUserService {

	public UserDetails getDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (hasAuthenticatedUser(authentication))
			return (UserDetails) authentication.getPrincipal();
		throw new UsernameNotFoundException("No authenticated user found");
	}

	private boolean hasAuthenticatedUser(Authentication authentication) {
		return Objects.nonNull(authentication) && authentication.getPrincipal() instanceof UserDetails;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getDetails().getAuthorities();
	}

	public Optional<User> getUser() {
		UserDetails userDetails = getDetails();
		if (userDetails instanceof User user)
			return Optional.of((User) userDetails);
		return Optional.empty();
	}

	public User getUserOrThrow() {
		return getUser().orElseThrow(() -> new UsernameNotFoundException("No authenticated user found"));
	}

}
