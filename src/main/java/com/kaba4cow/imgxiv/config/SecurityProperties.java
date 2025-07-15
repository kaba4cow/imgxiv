package com.kaba4cow.imgxiv.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "security")
@Configuration
public class SecurityProperties {

	private String[] whitelist;

}
