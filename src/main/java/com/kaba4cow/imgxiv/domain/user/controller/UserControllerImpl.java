package com.kaba4cow.imgxiv.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.domain.user.dto.UserDto;
import com.kaba4cow.imgxiv.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserControllerImpl implements UserController {

	private final UserService userService;

	@Override
	public ResponseEntity<UserDto> getUser(Long id) {
		return ResponseEntity.ok(userService.getUser(id));
	}

}
