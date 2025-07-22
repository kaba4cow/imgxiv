package com.kaba4cow.imgxiv.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.common.dto.UserIdRequest;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;
import com.kaba4cow.imgxiv.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController implements UserControllerApiDoc {

	private final UserService userService;

	@Override
	public ResponseEntity<UserDto> getUser(UserIdRequest request) {
		return ResponseEntity.ok(userService.getUser(request.getUserId()));
	}

}
