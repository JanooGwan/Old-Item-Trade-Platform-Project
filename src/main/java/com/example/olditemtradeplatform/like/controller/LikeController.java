package com.example.olditemtradeplatform.like.controller;

import com.example.olditemtradeplatform.like.dto.LikeResponseDTO;
import com.example.olditemtradeplatform.like.service.LikeService;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController implements LikeApi {

    private final LikeService likeService;

    @PostMapping("/{postId}")
    public ResponseEntity<LikeResponseDTO> toggleLike(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        LikeResponseDTO response = likeService.toggleLike(userDetails.getMember(), postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}/count")
    public ResponseEntity<Integer> countLikes(@PathVariable Long postId) {
        int count = likeService.countLikes(postId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{postId}/liked")
    public ResponseEntity<Boolean> isLikedByUser(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean liked = likeService.isPostLikedByMember(postId, userDetails.getMember());
        return ResponseEntity.ok(liked);
    }
}
