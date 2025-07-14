package com.example.olditemtradeplatform.postimage.service;

import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import com.example.olditemtradeplatform.postimage.domain.PostImage;
import com.example.olditemtradeplatform.postimage.domain.PostImageId;
import com.example.olditemtradeplatform.postimage.dto.PostImageRequestDTO;
import com.example.olditemtradeplatform.postimage.dto.PostImageResponseDTO;
import com.example.olditemtradeplatform.postimage.repository.PostImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final FileStorageService fileStorageService;

    public PostImageResponseDTO saveImage(PostImageRequestDTO dto) {

        MultipartFile file = dto.getFile();

        String imageUrl = fileStorageService.save(file);

        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없습니다."));

        Long imageAt = postImageRepository.countByPost(post) + 1;

        PostImage postImage = new PostImage(post, imageAt, imageUrl);
        postImageRepository.save(postImage);

        return PostImageResponseDTO.from(postImage);
    }


    @Transactional(readOnly = true)
    public PostImageResponseDTO findImage(PostImageId id) {
        PostImage postImage = postImageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PostImage not found"));
        return PostImageResponseDTO.from(postImage);
    }

    @Transactional
    public void deleteImage(PostImageId id) {
        postImageRepository.deleteById(id);
    }
}