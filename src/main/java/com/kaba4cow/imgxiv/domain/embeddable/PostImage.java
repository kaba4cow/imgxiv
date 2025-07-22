package com.kaba4cow.imgxiv.domain.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Embeddable
public class PostImage {

	@Column(name = "column_file_name", nullable = false, length = 256)
	private String fileName;

	@Column(name = "column_file_size", nullable = false)
	private long fileSize;

	@Column(name = "column_storage_key", nullable = false, length = 512)
	private String storageKey;

	@Column(name = "column_content_type", nullable = false, length = 64)
	private String contentType;

}
