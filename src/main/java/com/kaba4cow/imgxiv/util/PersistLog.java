package com.kaba4cow.imgxiv.util;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class PersistLog {

	public <T> T logPersist(T entity, CrudRepository<T, ?> repository) {
		T saved = repository.save(entity);
		log.info("Saved: {}", saved);
		return saved;
	}

}
