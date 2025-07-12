package com.example.olditemtradeplatform.post.service;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.dto.PostCreateRequestDTO;
import com.example.olditemtradeplatform.post.dto.PostUpdateRequestDTO;
import com.example.olditemtradeplatform.post.dto.PostViewResponseDTO;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public PostViewResponseDTO createPost(Long writerId, PostCreateRequestDTO dto) {
        Member writer = memberRepository.findById(writerId)
                .orElseThrow(() -> new IllegalArgumentException("Writer not found"));

        Post post = dto.toEntity(writer);
        postRepository.save(post);

        return PostViewResponseDTO.from(post);
    }

    @Transactional(readOnly = true)
    public PostViewResponseDTO findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return PostViewResponseDTO.from(post);
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateRequestDTO dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        post.updatePost(dto.getContent(), dto.getDealWay(), dto.getDealStatus());
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
