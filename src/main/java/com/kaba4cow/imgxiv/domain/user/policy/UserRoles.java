package com.kaba4cow.imgxiv.domain.user.policy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRoles {

	public static final String USER = "user";

	public static final String MODERATOR = "moderator";

	public static final String ADMIN = "admin";

}
