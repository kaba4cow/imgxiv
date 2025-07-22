package com.kaba4cow.imgxiv.auth.service;

import com.kaba4cow.imgxiv.auth.dto.AuthDto;
import com.kaba4cow.imgxiv.auth.dto.LoginRequest;
import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;

public interface AuthService {

	UserDto register(RegisterRequest request);

	AuthDto login(LoginRequest request);

}
