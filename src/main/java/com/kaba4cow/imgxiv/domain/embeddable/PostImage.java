package com.kaba4cow.imgxiv.domain.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
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
@Embeddable
public class PostImage {

	@Column(name = "column_file_name")
	private String fileName;

	@Column(name = "column_file_size")
	private long fileSize;

	@Column(name = "column_storage_path")
	private String storagePath;

	@Column(name = "column_content_type")
	private String contentType;

}
