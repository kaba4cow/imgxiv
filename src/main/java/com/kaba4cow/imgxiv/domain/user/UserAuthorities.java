package com.kaba4cow.imgxiv.domain.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAuthorities {

	public static final String CREATE_POST = "create-post";

	public static final String CREATE_CATEGORY = "create-category";

	public static final String EDIT_POST_NA = "edit-post-na";

	public static final String DELETE_POST_NA = "delete-post-na";

	public static final String DELETE_COMMENT_NA = "delete-comment-na";

	public static final String MANAGE_TAGS = "manage-tags";

	public static final String MANAGE_CATEGORIES = "manage-categories";

	public static final String VIEW_MODERATORS = "view-moderators";

	public static final String MANAGE_MODERATORS = "manage-moderators";

}
