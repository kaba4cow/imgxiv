package com.kaba4cow.imgxiv.domain.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.common.dto.PaginationParams;
import com.kaba4cow.imgxiv.common.dto.UserIdRequest;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;
import com.kaba4cow.imgxiv.domain.user.service.ModeratorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ModeratorController implements ModeratorControllerApiDoc {

	private final ModeratorService moderatorService;

	@Override
	public ResponseEntity<List<UserDto>> getModerators(PaginationParams pagination) {
		return ResponseEntity.ok(moderatorService.getModerators(pagination));
	}

	@Override
	public ResponseEntity<Void> assignModerator(UserIdRequest pagination) {
		moderatorService.assignModerator(pagination.getUserId());
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> removeModerator(UserIdRequest pagination) {
		moderatorService.removeModerator(pagination.getUserId());
		return ResponseEntity.noContent().build();
	}

}
