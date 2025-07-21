package com.kaba4cow.imgxiv.auth.registrar;

import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.domain.user.User;

public interface UserRegistrar {

	User registerUser(RegisterRequest request);

}
