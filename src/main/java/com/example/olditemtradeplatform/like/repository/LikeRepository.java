package com.example.olditemtradeplatform.like.repository;

import com.example.olditemtradeplatform.like.domain.Like;
import com.example.olditemtradeplatform.like.domain.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
}
