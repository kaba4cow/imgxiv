package com.kaba4cow.imgxiv.domain.user.service;

import java.util.List;

import com.kaba4cow.imgxiv.common.dto.PaginationParams;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;

public interface ModeratorService {

	List<UserDto> getModerators(PaginationParams pagination);

	void assignModerator(Long id);

	void removeModerator(Long id);

}
