package com.example.olditemtradeplatform.post.service;

import com.example.olditemtradeplatform.common.FileStore;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.dto.PostCreateRequestDTO;
import com.example.olditemtradeplatform.post.dto.PostDetailResponseDTO;
import com.example.olditemtradeplatform.post.dto.PostPreviewResponseDTO;
import com.example.olditemtradeplatform.post.dto.PostUpdateRequestDTO;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import com.example.olditemtradeplatform.postimage.domain.PostImage;
import com.example.olditemtradeplatform.postimage.repository.PostImageRepository;
import com.example.olditemtradeplatform.product.domain.Product;
import com.example.olditemtradeplatform.product.dto.ProductRequestDTO;
import com.example.olditemtradeplatform.product.repository.ProductRepository;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
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

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        post.increaseViewCount();

        boolean isAuthor = post.getWriter().getId().equals(currentUserId);

        boolean liked = post.getLikes().stream()
                .anyMatch(like -> like.getMember().getId().equals(currentUserId));

        return PostDetailResponseDTO.from(post, isAuthor, liked);

    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponseDTO> getPosts() {
        return postRepository.findAll().stream()
                .sorted(Comparator.comparing(Post::getId).reversed())
                .map(post -> {
                    long likeCount = post.getLikes().size();
                    String thumbnail = post.getPostImages().stream()
                            .filter(img -> img.getId().getImageAt() == 1)
                            .map(PostImage::getImageUrl)
                            .findFirst()
                            .orElse(null);

                    return PostPreviewResponseDTO.builder()
                            .postId(post.getId())
                            .writerName(post.getWriter().getNickname())
                            .title(post.getTitle())
                            .createdDate(post.getCreateDate())
                            .viewCount(post.getViewCount())
                            .likeCount(likeCount)
                            .buyOrSale(post.getBuyOrSale().name())
                            .thumbnailImageUrl(thumbnail)
                            .build();
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
                    throw new RuntimeException("이미지 저장 중 오류 발생", e);
                }
            }

        }

        return PostDetailResponseDTO.from(savedPost);
    }

    @Transactional(readOnly = true)
    public PostDetailResponseDTO findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return PostDetailResponseDTO.from(post);
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateRequestDTO dto, Long requesterId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if (!post.getWriter().getId().equals(requesterId)) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }

        post.updatePost(dto.getContent(), dto.getDealWay(), dto.getDealStatus());
    }

    @Transactional
    public void deletePost(Long postId, Long requesterId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if (!post.getWriter().getId().equals(requesterId)) {
            throw new AccessDeniedException("작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }

}