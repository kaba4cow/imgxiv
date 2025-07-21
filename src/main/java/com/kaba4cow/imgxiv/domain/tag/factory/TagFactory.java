package com.kaba4cow.imgxiv.domain.tag.factory;

import com.kaba4cow.imgxiv.domain.tag.Tag;
import com.kaba4cow.imgxiv.domain.tag.dto.TagCreateRequest;

public interface TagFactory {

	Tag createTag(TagCreateRequest request);

}
