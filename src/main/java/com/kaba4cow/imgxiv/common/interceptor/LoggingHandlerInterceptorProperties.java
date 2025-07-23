package com.kaba4cow.imgxiv.common.interceptor;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@ConditionalOnProperty(prefix = "interceptors.logging", name = "enable", havingValue = "true")
@ConfigurationProperties(prefix = "interceptors.logging")
public class LoggingHandlerInterceptorProperties {

	private final List<String> pathPatterns;

	private final List<String> excludedPathPatterns;

}
