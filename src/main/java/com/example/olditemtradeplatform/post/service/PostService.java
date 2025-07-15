package com.example.olditemtradeplatform.post.service;

import com.example.olditemtradeplatform.common.FileStore;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.dto.PostCreateRequestDTO;
import com.example.olditemtradeplatform.post.dto.PostResponseDTO;
import com.example.olditemtradeplatform.post.dto.PostUpdateRequestDTO;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import com.example.olditemtradeplatform.postimage.domain.PostImage;
import com.example.olditemtradeplatform.postimage.repository.PostImageRepository;
import com.example.olditemtradeplatform.product.domain.Product;
import com.example.olditemtradeplatform.product.dto.ProductRequestDTO;
import com.example.olditemtradeplatform.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final ProductRepository productRepository;
    private final FileStore fileStore;


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
    public PostResponseDTO createPost(Member writer, PostCreateRequestDTO postDto, ProductRequestDTO productDto, List<MultipartFile> images) {

        Post post = postDto.toEntity(writer);
        Post savedPost = postRepository.save(post);

        Product product = productDto.toEntity(post);
        productRepository.save(product);

        if (images != null && !images.isEmpty()) {
            int imageIndex = 0;
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
