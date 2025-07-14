package com.kaba4cow.imgxiv.auth.jwt;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String AUTH_HEADER = "Authorization";

	private static final String BEARER = "Bearer ";

	private final JwtService jwtService;

	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(//
			HttpServletRequest request, //
			HttpServletResponse response, //
			FilterChain filterChain//
	) throws ServletException, IOException {
		if (isAuthRequest(request))
			processAuthRequest(request);
		filterChain.doFilter(request, response);
	}

	private boolean isAuthRequest(HttpServletRequest request) {
		String authHeader = request.getHeader(AUTH_HEADER);
		return Objects.nonNull(authHeader) && authHeader.startsWith(BEARER);
	}

	private void processAuthRequest(HttpServletRequest request) {
		String jwt = extractToken(request);
		String username = jwtService.extractUsername(jwt);
		if (shouldAuthenticate(username))
			authenticate(jwt, username, request);
	}

	private String extractToken(HttpServletRequest request) {
		return request.getHeader(AUTH_HEADER).substring(BEARER.length());
	}

	private boolean shouldAuthenticate(String username) {
		return Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication());
	}

	private void authenticate(String jwt, String username, HttpServletRequest request) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		if (jwtService.isTokenValid(jwt, userDetails))
			createAuthentication(userDetails, request);
	}

	private void createAuthentication(UserDetails userDetails, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken authToken = createAuthToken(userDetails);
		WebAuthenticationDetails authDetails = createAuthDetials(request);
		authToken.setDetails(authDetails);
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

	private UsernamePasswordAuthenticationToken createAuthToken(UserDetails userDetails) {
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	private WebAuthenticationDetails createAuthDetials(HttpServletRequest request) {
		return new WebAuthenticationDetailsSource().buildDetails(request);
	}

}
