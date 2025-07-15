package com.kaba4cow.imgxiv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kaba4cow.imgxiv.auth.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthFilter;

	private final AuthenticationProvider authProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http//
				.csrf(AbstractHttpConfigurer::disable)//
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//
				.authorizeHttpRequests(requests -> requests.anyRequest().permitAll())//
				.authenticationProvider(authProvider)//
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)//
				.build();
	}

}
