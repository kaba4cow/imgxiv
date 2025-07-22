package com.kaba4cow.imgxiv.auth.dto;

import com.kaba4cow.imgxiv.domain.user.dto.UserDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthDto {

	private final String token;

	private final UserDto user;

}
