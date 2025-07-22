package com.kaba4cow.imgxiv.domain.user.dto;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.user.User;

@Component
public class ProfileMapper {

	public ProfileDto mapToDto(User user) {
		return new ProfileDto(//
				user.getId(), //
				user.getCredentials().getUsername(), //
				user.getCredentials().getEmail()//
		);
	}

}
