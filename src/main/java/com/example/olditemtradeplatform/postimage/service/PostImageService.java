package com.example.olditemtradeplatform.postimage.service;

import com.example.olditemtradeplatform.global.exception.CustomException;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import com.example.olditemtradeplatform.postimage.domain.PostImage;
import com.example.olditemtradeplatform.postimage.domain.PostImageId;
import com.example.olditemtradeplatform.postimage.dto.PostImageRequestDTO;
import com.example.olditemtradeplatform.postimage.dto.PostImageResponseDTO;
import com.example.olditemtradeplatform.postimage.exception.PostImageErrorCode;
import com.example.olditemtradeplatform.postimage.repository.PostImageRepository;
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
        MultipartFile file = dto.file();
        String imageUrl = fileStorageService.save(file);

        Post post = postRepository.findById(dto.postId())
                .orElseThrow(() -> new CustomException(PostImageErrorCode.POST_NOT_FOUND));

        Long imageAt = postImageRepository.countByPost(post) + 1;

        PostImage postImage = new PostImage(post, imageAt, imageUrl);
        postImageRepository.save(postImage);

        return PostImageResponseDTO.from(postImage);
    }

    @Transactional(readOnly = true)
    public PostImageResponseDTO findImage(PostImageId id) {
        PostImage postImage = postImageRepository.findById(id)
                .orElseThrow(() -> new CustomException(PostImageErrorCode.POST_IMAGE_NOT_FOUND));
        return PostImageResponseDTO.from(postImage);
    }

    @Transactional
    public void deleteImage(PostImageId id) {
        postImageRepository.deleteById(id);
    }
}
