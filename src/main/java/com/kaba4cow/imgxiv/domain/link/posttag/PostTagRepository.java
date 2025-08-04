package com.kaba4cow.imgxiv.domain.link.posttag;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaba4cow.imgxiv.domain.link.posttag.model.PostTag;
import com.kaba4cow.imgxiv.domain.link.posttag.model.PostTagId;

public interface PostTagRepository extends JpaRepository<PostTag, PostTagId> {

}
