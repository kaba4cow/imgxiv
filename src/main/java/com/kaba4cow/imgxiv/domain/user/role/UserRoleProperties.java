package com.kaba4cow.imgxiv.domain.user.role;

import java.util.Map;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@ConfigurationProperties(prefix = "roles")
public class UserRoleProperties {

	private Map<String, RoleDefinition> map;

	public RoleDefinition getRoleDefinition(String roleName) {
		return map.get(roleName);
	}

	@NoArgsConstructor
	@Getter
	@Setter
	public static class RoleDefinition {

		private String parent;

		private Set<String> authorities;

	}

}
