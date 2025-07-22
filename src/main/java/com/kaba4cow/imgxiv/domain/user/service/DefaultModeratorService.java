package com.kaba4cow.imgxiv.domain.user.service;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.common.exception.RoleAssignException;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.UserRole;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultModeratorService implements ModeratorService {

	private final UserRepository userRepository;

	@Override
	public void assignModerator(Long id) {
		User user = userRepository.findByIdOrThrow(id);
		ensureCanAssignModerator(user);
		saveUserRole(user, UserRole.MODERATOR);
	}

	private void ensureCanAssignModerator(User user) {
		if (user.getRole() == UserRole.ADMIN)
			throw new RoleAssignException("Cannot assign MODERATOR role to ADMIN user");
	}

	@Override
	public void removeModerator(Long id) {
		User user = userRepository.findByIdOrThrow(id);
		ensureCanRemoveModerator(user);
		saveUserRole(user, UserRole.USER);
	}

	private void ensureCanRemoveModerator(User user) {
		if (user.getRole() != UserRole.MODERATOR)
			throw new RoleAssignException("User is not MODERATOR");
	}

	private void saveUserRole(User user, UserRole role) {
		user.setRole(role);
		userRepository.save(user);
	}

}
