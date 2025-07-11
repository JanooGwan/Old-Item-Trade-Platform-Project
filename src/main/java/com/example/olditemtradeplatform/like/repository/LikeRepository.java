package com.example.olditemtradeplatform.like.repository;

import com.example.olditemtradeplatform.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Long, Like> {
}
