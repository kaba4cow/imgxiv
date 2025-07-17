package com.kaba4cow.imgxiv.auth.dto;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.user.User;

@Component
public class AuthUserMapper {

	public AuthUserDto mapToDto(User user) {
		return new AuthUserDto(//
				user.getUsername(), //
				user.getEmail()//
		);
	}

}
