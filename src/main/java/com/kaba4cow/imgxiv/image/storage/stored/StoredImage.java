package com.kaba4cow.imgxiv.image.storage.stored;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "table_stored_image")
public class StoredImage {

	@Id
	@Column(name = "column_storage_key", length = 512)
	private String storageKey;

	@ToString.Exclude
	@Lob
	@Column(name = "column_data", nullable = false)
	private byte[] data;

}
