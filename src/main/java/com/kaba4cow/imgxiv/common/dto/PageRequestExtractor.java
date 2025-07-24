package com.kaba4cow.imgxiv.common.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PageRequestExtractor {

	public PageRequest getPageRequest(PaginationParams pagination, String... properties) {
		return PageRequest.of(//
				getPageNumber(pagination), //
				getPageSize(pagination), //
				getSort(pagination, properties)//
		);
	}

	private int getPageNumber(PaginationParams pagination) {
		return pagination.getPage();
	}

	private int getPageSize(PaginationParams pagination) {
		return pagination.getSize();
	}

	private Sort getSort(PaginationParams pagination, String... properties) {
		return properties.length == 0//
				? Sort.by(pagination.getSort())//
				: Sort.by(pagination.getSort(), properties);
	}

}
