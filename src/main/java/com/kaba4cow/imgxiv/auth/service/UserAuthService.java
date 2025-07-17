package com.kaba4cow.imgxiv.auth.service;

import java.util.Optional;

import com.kaba4cow.imgxiv.auth.dto.AuthResponse;
import com.kaba4cow.imgxiv.auth.dto.LoginRequest;
import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.auth.dto.AuthUserDto;
import com.kaba4cow.imgxiv.domain.user.User;

public interface UserAuthService {

	AuthUserDto register(RegisterRequest request);

	AuthResponse login(LoginRequest request);

	Optional<User> findByUsernameOrEmail(String usernameOrEmail);

}
