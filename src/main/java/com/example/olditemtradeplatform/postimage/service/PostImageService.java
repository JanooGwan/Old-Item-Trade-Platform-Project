package com.example.olditemtradeplatform.postimage.service;

import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import com.example.olditemtradeplatform.postimage.domain.PostImage;
import com.example.olditemtradeplatform.postimage.domain.PostImageId;
import com.example.olditemtradeplatform.postimage.dto.PostImageRequestDTO;
import com.example.olditemtradeplatform.postimage.dto.PostImageResponseDTO;
import com.example.olditemtradeplatform.postimage.repository.PostImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostImageResponseDTO create(Long postId, PostImageRequestDTO dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        PostImage postImage = dto.toEntity(post);
        postImageRepository.save(postImage);

        return PostImageResponseDTO.from(postImage);
    }

    @Transactional(readOnly = true)
    public PostImageResponseDTO find(Long postId, Long imageAt) {
        PostImage postImage = postImageRepository.findById(new PostImageId(postId, imageAt))
                .orElseThrow(() -> new IllegalArgumentException("PostImage not found"));
        return PostImageResponseDTO.from(postImage);
    }

    @Transactional
    public void delete(Long postId, Long imageAt) {
        postImageRepository.deleteById(new PostImageId(postId, imageAt));
    }
}