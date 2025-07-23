package com.kaba4cow.imgxiv.common.interceptor;

import java.util.List;
import java.util.Objects;

import org.springframework.web.servlet.HandlerInterceptor;

public interface ConfigurableHandlerInterceptor extends HandlerInterceptor {

	List<String> getPathPatterns();

	default boolean hasPathPatterns() {
		return Objects.nonNull(getPathPatterns()) && !getPathPatterns().isEmpty();
	}

	List<String> getExcludedPathPatterns();

	default boolean hasExcludedPathPatterns() {
		return Objects.nonNull(getExcludedPathPatterns()) && !getExcludedPathPatterns().isEmpty();
	}

}
