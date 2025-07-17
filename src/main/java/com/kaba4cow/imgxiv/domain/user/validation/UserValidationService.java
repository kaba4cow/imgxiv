package com.kaba4cow.imgxiv.domain.user.validation;

import com.kaba4cow.imgxiv.common.exception.EmailConflictException;
import com.kaba4cow.imgxiv.common.exception.PasswordMismatchException;
import com.kaba4cow.imgxiv.common.exception.UsernameConflictException;

public interface UserValidationService {

	void ensureUsernameAvailable(String username) throws UsernameConflictException;

	void ensureEmailAvailable(String email) throws EmailConflictException;

	void ensurePasswordsMatch(String password, String passwordHash) throws PasswordMismatchException;

}
