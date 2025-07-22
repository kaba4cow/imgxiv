package com.kaba4cow.imgxiv.auth.service;

import com.kaba4cow.imgxiv.auth.dto.AuthDto;
import com.kaba4cow.imgxiv.auth.dto.AuthRequest;
import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ProfileDto;

public interface AuthService {

	ProfileDto register(RegisterRequest request);

	AuthDto authenticate(AuthRequest request);

}
