package com.example.olditemtradeplatform.post.controller;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.dto.PostCreateRequestDTO;
import com.example.olditemtradeplatform.post.dto.PostResponseDTO;
import com.example.olditemtradeplatform.post.service.PostService;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(
            @RequestBody PostCreateRequestDTO requestDto,
            Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member writer = userDetails.getMember();

        PostResponseDTO created = postService.createPost(writer, requestDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getPosts() {
        List<PostResponseDTO> posts = postService.getPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long postId) {
        PostResponseDTO post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }
}
