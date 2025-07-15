package com.kaba4cow.imgxiv.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	private final String secretKey;

	private final long expirationMillis;

}
