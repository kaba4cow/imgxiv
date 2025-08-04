package com.kaba4cow.imgxiv.domain.user.service;

import com.kaba4cow.imgxiv.domain.user.dto.ChangeEmailRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangePasswordRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeUsernameRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ProfileDto;
import com.kaba4cow.imgxiv.domain.user.model.User;

public interface ProfileService {

	ProfileDto getProfile(User user);

	ProfileDto changeUsername(ChangeUsernameRequest request, User user);

	ProfileDto changeEmail(ChangeEmailRequest request, User user);

	void changePassword(ChangePasswordRequest request, User user);

}
