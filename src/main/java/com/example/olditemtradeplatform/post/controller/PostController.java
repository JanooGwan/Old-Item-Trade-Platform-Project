package com.example.olditemtradeplatform.post.controller;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.service.MemberService;
import com.example.olditemtradeplatform.post.domain.DealStatus;
import com.example.olditemtradeplatform.post.dto.PostCreateRequestDTO;
import com.example.olditemtradeplatform.post.dto.PostDetailResponseDTO;
import com.example.olditemtradeplatform.post.dto.PostPreviewResponseDTO;
import com.example.olditemtradeplatform.post.dto.PostUpdateRequestDTO;
import com.example.olditemtradeplatform.post.service.PostService;
import com.example.olditemtradeplatform.product.dto.ProductRequestDTO;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;


    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDTO> getPost(@PathVariable Long postId, Authentication authentication) {
        PostDetailResponseDTO post = postService.getPost(postId, authentication);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<List<PostPreviewResponseDTO>> getPosts(
            @RequestParam(value = "dealStatus", required = false) DealStatus dealStatus
    ) {
        List<PostPreviewResponseDTO> posts = postService.getPosts(dealStatus);
        return ResponseEntity.ok(posts);
    }


    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<PostDetailResponseDTO> createPost(
            @Valid @RequestPart("post") PostCreateRequestDTO postRequestDto,
            @Valid @RequestPart("product") ProductRequestDTO productRequestDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member writer = userDetails.getMember();
        PostDetailResponseDTO created = postService.createPost(writer, postRequestDto, productRequestDto, images);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDTO dto,
            Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long currentUserId = userDetails.getMember().getId();

        postService.updatePost(postId, dto, currentUserId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long currentUserId = userDetails.getMember().getId();

        postService.deletePost(postId, currentUserId);
        return ResponseEntity.noContent().build();
    }

}