package com.example.olditemtradeplatform.postimage.repository;

import com.example.olditemtradeplatform.postimage.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<Long, PostImage> {
}
