package com.kaba4cow.imgxiv.domain.user.dto;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.user.User;

@Component
public class UserMapper {

	public UserDto mapToDto(User user) {
		return new UserDto(//
				user.getId(), //
				user.getCredentials().getUsername(), //
				user.getCredentials().getEmail()//
		);
	}

}
