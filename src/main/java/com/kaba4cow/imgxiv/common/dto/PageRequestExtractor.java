package com.kaba4cow.imgxiv.common.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PageRequestExtractor {

	public <T extends RequestWithPagination> PageRequest getPageRequest(T request) {
		return PageRequest.of(//
				getPageNumber(request), //
				getPageSize(request)//
		);
	}

	public <T extends RequestWithPagination & RequestWithSorting> PageRequest getSortedPageRequest(T request,
			String... properties) {
		return PageRequest.of(//
				getPageNumber(request), //
				getPageSize(request), //
				properties.length == 0//
						? getSort(request)//
						: getSort(request, properties)//
		);
	}

	private int getPageNumber(RequestWithPagination request) {
		return request.getPagination().getPage();
	}

	private int getPageSize(RequestWithPagination request) {
		return request.getPagination().getSize();
	}

	private Sort getSort(RequestWithSorting request) {
		return Sort.by(request.getSorting().getDirection());
	}

	private Sort getSort(RequestWithSorting request, String... properties) {
		return Sort.by(request.getSorting().getDirection(), properties);
	}

}
