package com.kaba4cow.imgxiv.domain.user.service;

public interface AdminService {

	void assignModerator(Long id);

	void removeModerator(Long id);

}
