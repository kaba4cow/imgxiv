package com.kaba4cow.imgxiv.auth.dto;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.user.User;

@Component
public class UserMapper {

	public UserDto mapToDto(User user) {
		return new UserDto(//
				user.getUsername(), //
				user.getEmail()//
		);
	}

}
