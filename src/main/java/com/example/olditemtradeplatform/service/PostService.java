package com.example.olditemtradeplatform.service;

import com.example.olditemtradeplatform.dto.post.PostCreateRequestDTO;
import com.example.olditemtradeplatform.entity.Member;
import com.example.olditemtradeplatform.entity.Post;
import com.example.olditemtradeplatform.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post createPost(PostCreateRequestDTO dto, Member writer) {
        return dto.toEntity(writer);
    }

    @Transactional
    public Post updatePost() {
        return new Post
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
