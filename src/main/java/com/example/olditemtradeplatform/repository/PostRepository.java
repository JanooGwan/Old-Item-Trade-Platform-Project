package com.example.olditemtradeplatform.repository;

import com.example.olditemtradeplatform.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByWriterId(Long writerId);
}
