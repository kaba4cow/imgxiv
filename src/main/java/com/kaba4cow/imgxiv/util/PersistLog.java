package com.kaba4cow.imgxiv.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersistLog {

	public static <T> T log(T entity) {
		log.info("Saved: {}", entity);
		return entity;
	}

}
