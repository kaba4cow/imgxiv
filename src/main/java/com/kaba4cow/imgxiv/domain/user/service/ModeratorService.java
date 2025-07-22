package com.kaba4cow.imgxiv.domain.user.service;

import java.util.List;

import com.kaba4cow.imgxiv.common.dto.PageableRequest;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;

public interface ModeratorService {

	List<UserDto> getModerators(PageableRequest request);

	void assignModerator(Long id);

	void removeModerator(Long id);

}
