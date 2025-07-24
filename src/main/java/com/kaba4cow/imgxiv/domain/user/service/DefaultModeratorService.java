package com.kaba4cow.imgxiv.domain.user.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.common.dto.PaginationParams;
import com.kaba4cow.imgxiv.common.exception.RoleAssignException;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.UserRole;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;
import com.kaba4cow.imgxiv.domain.user.dto.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultModeratorService implements ModeratorService {

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	@Override
	public List<UserDto> getModerators(PaginationParams pagination) {
		PageRequest pageRequest = pagination.toPageRequest("createdAt.timestamp");
		return userRepository.findAllByRole(UserRole.MODERATOR, pageRequest).stream()//
				.map(userMapper::mapToDto)//
				.toList();
	}

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
		User saved = userRepository.save(user);
		log.info("Assigned {} role to {}", role, saved);
	}

}
