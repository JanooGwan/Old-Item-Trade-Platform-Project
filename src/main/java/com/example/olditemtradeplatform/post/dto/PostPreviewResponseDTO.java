package com.example.olditemtradeplatform.post.dto;

import com.example.olditemtradeplatform.post.domain.Post;

import java.time.LocalDateTime;

public record PostPreviewResponseDTO(
        Long postId,
        String writerName,
        String title,
        LocalDateTime createdDate,
        Long viewCount,
        Long likeCount,
        String buyOrSale,
        String thumbnailImageUrl,
        String dealStatus
) {
    public static PostPreviewResponseDTO of(Post post, String thumbnailImageUrl) {
        return new PostPreviewResponseDTO(
                post.getId(),
                post.getWriter().getNickname(),
                post.getTitle(),
                post.getCreateDate(),
                post.getViewCount(),
                (long) post.getLikes().size(),
                post.getBuyOrSale().name(),
                thumbnailImageUrl,
                post.getDealStatus().name()
        );
    }

    public static PostPreviewResponseDTO from(Post post) {
        return new PostPreviewResponseDTO(
                post.getId(),
                post.getWriter().getNickname(),
                post.getTitle(),
                post.getCreateDate(),
                post.getViewCount(),
                (long) post.getLikes().size(),
                post.getBuyOrSale().name(),
                post.getPostImages().isEmpty() ? null : post.getPostImages().get(0).getImageUrl(),
                post.getDealStatus().name()
        );
    }
}
