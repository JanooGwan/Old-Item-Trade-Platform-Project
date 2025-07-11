package com.example.olditemtradeplatform.post.repository;

import com.example.olditemtradeplatform.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByWriterId(Long writerId);
}
