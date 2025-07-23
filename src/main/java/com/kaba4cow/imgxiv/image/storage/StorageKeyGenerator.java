package com.kaba4cow.imgxiv.image.storage;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class StorageKeyGenerator {

	public String generateKey(String filename) {
		return String.format("%s/%s/%s", //
				getDateString(), //
				getHashString(filename), //
				getUUIDString()//
		);
	}

	private String getDateString() {
		LocalDateTime now = LocalDateTime.now();
		return String.format("%04d%02d%02d", //
				now.getYear(), //
				now.getMonthValue(), //
				now.getDayOfMonth()//
		);
	}

	private String getHashString(String filename) {
		return String.format("%08x", filename.hashCode());
	}

	private String getUUIDString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
