package com.kaba4cow.imgxiv.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserDto {

	private final Long id;

	private final String username;

	private final String email;

}
