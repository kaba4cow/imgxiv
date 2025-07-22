package com.kaba4cow.imgxiv.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeEmailRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangePasswordRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeUsernameRequest;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;
import com.kaba4cow.imgxiv.domain.user.dto.UserMapper;
import com.kaba4cow.imgxiv.domain.user.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProfileController implements ProfileControllerApiDoc {

	private final ProfileService profileService;

	private final UserMapper userMapper;

	@Override
	public ResponseEntity<UserDto> getUserInfo(User user) {
		return ResponseEntity.ok(userMapper.mapToDto(user));
	}

	@Override
	public ResponseEntity<UserDto> changeUsername(ChangeUsernameRequest request, User user) {
		UserDto result = profileService.changeUsername(request, user);
		return ResponseEntity.ok(result);
	}

	@Override
	public ResponseEntity<UserDto> changeEmail(ChangeEmailRequest request, User user) {
		UserDto result = profileService.changeEmail(request, user);
		return ResponseEntity.ok(result);
	}

	@Override
	public ResponseEntity<Void> changePassword(ChangePasswordRequest request, User user) {
		profileService.changePassword(request, user);
		return ResponseEntity.noContent().build();
	}

}
