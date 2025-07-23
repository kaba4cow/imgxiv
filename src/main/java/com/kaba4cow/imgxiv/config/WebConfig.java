package com.kaba4cow.imgxiv.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kaba4cow.imgxiv.common.interceptor.ConfigurableHandlerInterceptor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final List<ConfigurableHandlerInterceptor> interceptors;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		interceptors.forEach(interceptor -> {
			InterceptorRegistration registration = registry.addInterceptor(interceptor);
			if (interceptor.hasPathPatterns())
				registration.addPathPatterns(interceptor.getPathPatterns());
			if (interceptor.hasExcludedPathPatterns())
				registration.excludePathPatterns(interceptor.getExcludedPathPatterns());
		});
	}

}
