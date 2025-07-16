package com.kaba4cow.imgxiv.util;

import org.springframework.data.repository.CrudRepository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersistLog {

	public static <T> T logPersist(T entity, CrudRepository<T, ?> repository) {
		T saved = repository.save(entity);
		log.info("Saved: {}", saved);
		return saved;
	}

}
