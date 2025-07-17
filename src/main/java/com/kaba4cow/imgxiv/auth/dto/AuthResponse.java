package com.kaba4cow.imgxiv.auth.dto;

import com.kaba4cow.imgxiv.domain.user.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthResponse {

	private String token;

	private UserDto user;

}
