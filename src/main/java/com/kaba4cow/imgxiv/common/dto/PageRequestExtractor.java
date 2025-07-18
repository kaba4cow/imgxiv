package com.kaba4cow.imgxiv.common.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PageRequestExtractor {

	public PageRequest getSortedPageRequest(PaginationRequest request, String... properties) {
		return PageRequest.of(//
				getPageNumber(request), //
				getPageSize(request), //
				properties.length == 0//
						? getSort(request)//
						: getSort(request, properties)//
		);
	}

	private int getPageNumber(PaginationRequest request) {
		return request.getPageNumber();
	}

	private int getPageSize(PaginationRequest request) {
		return request.getPageSize();
	}

	private Sort getSort(PaginationRequest request) {
		return Sort.by(request.getSortDirection());
	}

	private Sort getSort(PaginationRequest request, String... properties) {
		return Sort.by(request.getSortDirection(), properties);
	}

}
