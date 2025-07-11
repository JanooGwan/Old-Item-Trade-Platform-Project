package com.example.olditemtradeplatform.post.service;

import com.example.olditemtradeplatform.post.dto.PostCreateRequestDTO;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.repository.PostRepository;
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
        return new Post();
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
