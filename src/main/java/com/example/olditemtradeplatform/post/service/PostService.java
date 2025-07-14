package com.example.olditemtradeplatform.post.service;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.dto.PostCreateRequestDTO;
import com.example.olditemtradeplatform.post.dto.PostResponseDTO;
import com.example.olditemtradeplatform.post.dto.PostUpdateRequestDTO;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    public PostResponseDTO getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return PostResponseDTO.from(post);
    }

    public List<PostResponseDTO> getPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostResponseDTO::from)
                .toList();
    }

    @Transactional
    public PostResponseDTO createPost(Member writer, PostCreateRequestDTO dto) {
        Post post = dto.toEntity(writer);
        Post savedPost = postRepository.save(post);
        return PostResponseDTO.from(savedPost);
    }

    @Transactional(readOnly = true)
    public PostResponseDTO findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return PostResponseDTO.from(post);
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
