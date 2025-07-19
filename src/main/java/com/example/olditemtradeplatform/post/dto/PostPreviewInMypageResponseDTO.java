package com.example.olditemtradeplatform.post.dto;

import com.example.olditemtradeplatform.post.domain.Post;

public record PostPreviewInMypageResponseDTO(
        Long postId,
        String title,
        Long likeCount,
        Long viewCount
) {
    public static PostPreviewInMypageResponseDTO from(Post post) {
        return new PostPreviewInMypageResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getLikeCount(),
                post.getViewCount()
        );
    }
}
