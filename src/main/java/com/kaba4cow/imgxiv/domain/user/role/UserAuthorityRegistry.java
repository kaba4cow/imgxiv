package com.kaba4cow.imgxiv.domain.user.role;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.user.UserRole;
import com.kaba4cow.imgxiv.domain.user.role.UserRoleProperties.RoleDefinition;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserAuthorityRegistry {

	private final UserRoleProperties userRoleProperties;

	private final Map<UserRole, Set<? extends GrantedAuthority>> roleAuthorities = new EnumMap<>(UserRole.class);

	@PostConstruct
	public void assignAuthoritiesToRoles() {
		for (UserRole role : UserRole.values()) {
			Set<String> authorities = resolveAuthorities(role.name());
			log.info("Assigned authorities to role {}: {}", role, authorities);
			roleAuthorities.put(role, toGrantedAuthorities(authorities));
		}
	}

	private Set<? extends GrantedAuthority> toGrantedAuthorities(Set<String> authorities) {
		return authorities.stream()//
				.map(SimpleGrantedAuthority::new)//
				.collect(Collectors.toUnmodifiableSet());
	}

	private Set<String> resolveAuthorities(String roleName) {
		String role = roleName.toLowerCase();
		Set<String> authorities = new HashSet<>();
		RoleDefinition roleDefinition = userRoleProperties.getRoleDefinition(role);
		if (Objects.nonNull(roleDefinition)) {
			authorities.add("ROLE_".concat(role));
			authorities.addAll(roleDefinition.getAuthorities());
			authorities.addAll(resolveAuthorities(roleDefinition.getParent()));
		}
		return Collections.unmodifiableSet(authorities);
	}

	public Set<? extends GrantedAuthority> getAuthorities(UserRole role) {
		return roleAuthorities.getOrDefault(role, Collections.emptySet());
	}

}
