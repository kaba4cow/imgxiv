package com.kaba4cow.imgxiv.common.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.kaba4cow.imgxiv.auth.context.CurrentUserService;
import com.kaba4cow.imgxiv.domain.user.User;

public abstract class CurrentUserAwareController {

	private CurrentUserService currentUserService;

	@Autowired
	public void setCurrentUserService(CurrentUserService currentUserService) {
		this.currentUserService = currentUserService;
	}

	public User getCurrentUser() {
		return currentUserService.getUserOrThrow();
	}

}
