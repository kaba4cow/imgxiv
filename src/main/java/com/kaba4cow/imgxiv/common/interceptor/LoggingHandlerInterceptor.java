package com.kaba4cow.imgxiv.common.interceptor;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.common.condition.ConditionalOnPropertyEnabled;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Getter
@ConditionalOnPropertyEnabled(prefix = LoggingHandlerInterceptor.PROPERTIES_PATH)
@Component
public class LoggingHandlerInterceptor implements ConfigurableHandlerInterceptor {

	static final String PROPERTIES_PATH = "interceptors.logging";

	private static final String START_TIME_ATTRIBUTE = "startTime";

	private final LoggingHandlerInterceptorProperties properties;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute(START_TIME_ATTRIBUTE, System.currentTimeMillis());
		log.info("Called {} {} from {} (User-Agent: {})", //
				request.getMethod(), //
				request.getRequestURI(), //
				request.getRemoteAddr(), //
				request.getHeader("User-Agent")//
		);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		long totalTimeMillis = calculateTotalTimeMillis(request);
		log.info("Completed {} {}, status {}, took {} ms", //
				request.getMethod(), //
				request.getRequestURI(), //
				HttpStatus.valueOf(response.getStatus()), //
				totalTimeMillis//
		);
		if (Objects.nonNull(exception))
			log.warn("Completed with uncaught exception: {} - {}", //
					exception.getClass().getSimpleName(), //
					exception.getMessage(), //
					exception//
			);
	}

	private long calculateTotalTimeMillis(HttpServletRequest request) {
		try {
			long startTimeMillis = (long) request.getAttribute(START_TIME_ATTRIBUTE);
			return System.currentTimeMillis() - startTimeMillis;
		} catch (Exception exception) {
			log.error("Could not calculate request completion time", exception);
			return -1L;
		}
	}

	@Override
	public List<String> getPathPatterns() {
		return properties.getPathPatterns();
	}

	@Override
	public List<String> getExcludedPathPatterns() {
		return properties.getExcludedPathPatterns();
	}

}
