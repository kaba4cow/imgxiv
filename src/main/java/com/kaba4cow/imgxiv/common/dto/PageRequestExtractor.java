package com.kaba4cow.imgxiv.common.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PageRequestExtractor {

	public PageRequest getPageRequest(PageableRequest request, String... properties) {
		return PageRequest.of(//
				getPageNumber(request), //
				getPageSize(request), //
				getSort(request, properties)//
		);
	}

	private int getPageNumber(PageableRequest request) {
		return request.getPageNumber();
	}

	private int getPageSize(PageableRequest request) {
		return request.getPageSize();
	}

	private Sort getSort(PageableRequest request, String... properties) {
		return properties.length == 0//
				? Sort.by(request.getSortDirection())//
				: Sort.by(request.getSortDirection(), properties);
	}

}
