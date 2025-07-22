package com.kaba4cow.imgxiv.auth.dto;

import com.kaba4cow.imgxiv.domain.user.dto.ProfileDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthDto {

	private String token;

	private ProfileDto profile;

}
