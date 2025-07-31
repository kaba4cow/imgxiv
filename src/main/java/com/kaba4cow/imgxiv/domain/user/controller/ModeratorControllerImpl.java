package com.kaba4cow.imgxiv.domain.user.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.domain.user.dto.UserDto;
import com.kaba4cow.imgxiv.domain.user.service.ModeratorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ModeratorControllerImpl implements ModeratorController {

	private final ModeratorService moderatorService;

	@Override
	public ResponseEntity<List<UserDto>> getModerators(Pageable pageable) {
		return ResponseEntity.ok(moderatorService.getModerators(pageable));
	}

	@Override
	public ResponseEntity<Void> assignModerator(Long id) {
		moderatorService.assignModerator(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> removeModerator(Long id) {
		moderatorService.removeModerator(id);
		return ResponseEntity.noContent().build();
	}

}
