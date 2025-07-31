package com.kaba4cow.imgxiv.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeEmailRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangePasswordRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeUsernameRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ProfileDto;
import com.kaba4cow.imgxiv.domain.user.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProfileControllerImpl implements ProfileController {

	private final ProfileService profileService;

	@Override
	public ResponseEntity<ProfileDto> getUserInfo(User user) {
		return ResponseEntity.ok(profileService.getProfile(user));
	}

	@Override
	public ResponseEntity<ProfileDto> changeUsername(ChangeUsernameRequest request, User user) {
		ProfileDto result = profileService.changeUsername(request, user);
		return ResponseEntity.ok(result);
	}

	@Override
	public ResponseEntity<ProfileDto> changeEmail(ChangeEmailRequest request, User user) {
		ProfileDto result = profileService.changeEmail(request, user);
		return ResponseEntity.ok(result);
	}

	@Override
	public ResponseEntity<Void> changePassword(ChangePasswordRequest request, User user) {
		profileService.changePassword(request, user);
		return ResponseEntity.noContent().build();
	}

}
