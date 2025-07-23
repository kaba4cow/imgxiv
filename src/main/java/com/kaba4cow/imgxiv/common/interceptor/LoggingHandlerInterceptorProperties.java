package com.kaba4cow.imgxiv.common.interceptor;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.kaba4cow.imgxiv.common.condition.ConditionalOnPropertyEnabled;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@ConditionalOnPropertyEnabled(prefix = LoggingHandlerInterceptor.PROPERTIES_PATH)
@ConfigurationProperties(prefix = LoggingHandlerInterceptor.PROPERTIES_PATH)
public class LoggingHandlerInterceptorProperties {

	private final List<String> pathPatterns;

	private final List<String> excludedPathPatterns;

}
