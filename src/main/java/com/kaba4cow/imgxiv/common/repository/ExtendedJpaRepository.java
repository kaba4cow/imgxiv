package com.kaba4cow.imgxiv.common.repository;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;

public interface ExtendedJpaRepository<T, ID> extends JpaRepository<T, ID> {

	default T findByIdOrThrow(ID id) {
		return findById(id).orElseThrow(() -> new NotFoundException(String.format("Not found: %s", id)));
	}

	default Set<T> findByIdsOrThrow(Collection<? extends ID> ids) {
		return ids.stream()//
				.distinct()//
				.map(this::findByIdOrThrow)//
				.collect(Collectors.toSet());
	}

}
