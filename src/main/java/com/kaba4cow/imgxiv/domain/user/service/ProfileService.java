package com.kaba4cow.imgxiv.domain.user.service;

import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeEmailRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangePasswordRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeUsernameRequest;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;

public interface ProfileService {

	UserDto changeUsername(ChangeUsernameRequest request, User user);

	UserDto changeEmail(ChangeEmailRequest request, User user);

	void changePassword(ChangePasswordRequest request, User user);

}
