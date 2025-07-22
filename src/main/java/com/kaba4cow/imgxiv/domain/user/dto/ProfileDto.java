package com.kaba4cow.imgxiv.domain.user.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfileDto {

	private Long id;

	private String username;

	private String email;

	private LocalDateTime createdAt;

}
