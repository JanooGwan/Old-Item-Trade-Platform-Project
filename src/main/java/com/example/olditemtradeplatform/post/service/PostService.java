package com.example.olditemtradeplatform.post.service;

import com.example.olditemtradeplatform.global.exception.CustomException;
import com.example.olditemtradeplatform.global.filesystem.FileStore;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.DealStatus;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.dto.*;
import com.example.olditemtradeplatform.post.exception.PostErrorCode;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import com.example.olditemtradeplatform.postimage.domain.PostImage;
import com.example.olditemtradeplatform.postimage.repository.PostImageRepository;
import com.example.olditemtradeplatform.product.domain.Product;
import com.example.olditemtradeplatform.product.dto.ProductRequestDTO;
import com.example.olditemtradeplatform.product.repository.ProductRepository;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final ProductRepository productRepository;
    private final FileStore fileStore;

    @Transactional
    public PostDetailResponseDTO getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));
        post.increaseViewCount();
        return PostDetailResponseDTO.from(post);
    }

    @Transactional
    public PostDetailResponseDTO getPost(Long postId, Authentication authentication) {
        Long currentUserId;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails userDetails) {
                currentUserId = userDetails.getMember().getId();
            } else {
                currentUserId = null;
            }
        } else {
            currentUserId = null;
        }

        postRepository.incrementViewCount(postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));

        boolean isAuthor = post.getWriter().getId().equals(currentUserId);
        boolean liked = post.getLikes().stream()
                .anyMatch(like -> like.getMember().getId().equals(currentUserId));

        return PostDetailResponseDTO.of(post, isAuthor, liked);
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponseDTO> getPosts() {
        return postRepository.findAll().stream()
                .sorted(Comparator.comparing(Post::getId).reversed())
                .map(post -> {
                    String thumbnail = post.getPostImages().stream()
                            .filter(img -> img.getId().getImageAt() == 1)
                            .map(PostImage::getImageUrl)
                            .findFirst()
                            .orElse(null);

                    return PostPreviewResponseDTO.of(post, thumbnail);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponseDTO> getPosts(DealStatus dealStatus) {
        List<Post> posts;

        if (dealStatus != null) {
            posts = postRepository.findByDealStatus(dealStatus);
        } else {
            posts = postRepository.findAll();
        }

        return posts.stream()
                .sorted(Comparator.comparing(Post::getId).reversed())
                .map(post -> {
                    String thumbnail = post.getPostImages().stream()
                            .filter(img -> img.getId().getImageAt() == 1)
                            .map(PostImage::getImageUrl)
                            .findFirst()
                            .orElse(null);

                    return PostPreviewResponseDTO.of(post, thumbnail);
                })
                .toList();
    }

    @Transactional
    public PostDetailResponseDTO createPost(Member writer, PostCreateRequestDTO postDto, ProductRequestDTO productDto, List<MultipartFile> images) {
        Post post = postDto.toEntity(writer);
        Post savedPost = postRepository.save(post);

        Product product = productDto.toEntity(post);
        productRepository.save(product);

        savedPost.getProducts().add(product);

        if (images != null && !images.isEmpty()) {
            int imageIndex = 1;
            for (MultipartFile image : images) {
                try {
                    String imageUrl = fileStore.saveFile(image);
                    PostImage postImage = new PostImage(savedPost, (long) imageIndex++, imageUrl);
                    postImageRepository.save(postImage);
                } catch (IOException e) {
                    throw new CustomException(PostErrorCode.FILE_SAVE_FAILED);
                }
            }
        }

        return PostDetailResponseDTO.from(savedPost);
    }

    @Transactional(readOnly = true)
    public PostDetailResponseDTO findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));
        return PostDetailResponseDTO.from(post);
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateRequestDTO dto, Long requesterId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));

        if (!post.getWriter().getId().equals(requesterId)) {
            throw new CustomException(PostErrorCode.NOT_AUTHOR);
        }

        post.updatePost(dto.getContent(), dto.getDealWay(), dto.getDealStatus());
    }

    @Transactional
    public void deletePost(Long postId, Long requesterId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));

        if (!post.getWriter().getId().equals(requesterId)) {
            throw new CustomException(PostErrorCode.NOT_AUTHOR);
        }

        postRepository.delete(post);
    }
}
