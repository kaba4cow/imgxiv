package com.kaba4cow.imgxiv.auth.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.role.UserAuthorityRegistry;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	private final UserAuthorityRegistry userAuthorityRegistry;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		return userRepository.findById(Long.valueOf(userId))//
				.map(user -> UserDetailsAdapter.of(user, userAuthorityRegistry))//
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

}
