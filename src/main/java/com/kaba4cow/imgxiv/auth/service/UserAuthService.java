package com.kaba4cow.imgxiv.auth.service;

import java.util.Optional;

import com.kaba4cow.imgxiv.auth.dto.AuthDto;
import com.kaba4cow.imgxiv.auth.dto.LoginRequest;
import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;

public interface UserAuthService {

	UserDto register(RegisterRequest request);

	AuthDto login(LoginRequest request);

	Optional<User> findByUsernameOrEmail(String usernameOrEmail);

}
