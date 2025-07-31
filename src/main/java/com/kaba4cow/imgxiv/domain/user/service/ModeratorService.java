package com.kaba4cow.imgxiv.domain.user.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.kaba4cow.imgxiv.domain.user.dto.UserDto;

public interface ModeratorService {

	List<UserDto> getModerators(Pageable pageable);

	void assignModerator(Long id);

	void removeModerator(Long id);

}
