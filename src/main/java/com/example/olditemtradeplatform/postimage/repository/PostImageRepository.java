package com.example.olditemtradeplatform.postimage.repository;

import com.example.olditemtradeplatform.postimage.domain.PostImage;
import com.example.olditemtradeplatform.postimage.domain.PostImageId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, PostImageId> {
}
