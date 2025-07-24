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
		return request.getPage();
	}

	private int getPageSize(PageableRequest request) {
		return request.getSize();
	}

	private Sort getSort(PageableRequest request, String... properties) {
		return properties.length == 0//
				? Sort.by(request.getSort())//
				: Sort.by(request.getSort(), properties);
	}

}
