package com.example.olditemtradeplatform.like.dto;

import com.example.olditemtradeplatform.like.domain.Like;

public record LikeResponseDTO(
        Long postId,
        Long memberId,
        boolean liked,
        Long likeCount
) {
    public static LikeResponseDTO from(Like like) {
        return new LikeResponseDTO(
                like.getPost().getId(),
                like.getMember().getId(),
                true,
                0L
        );
    }

    public static LikeResponseDTO of(Like like, boolean liked, Long likeCount) {
        return new LikeResponseDTO(
                like.getPost().getId(),
                like.getMember().getId(),
                liked,
                likeCount
        );
    }

    public static LikeResponseDTO of(Long postId, Long memberId, boolean liked, Long likeCount) {
        return new LikeResponseDTO(postId, memberId, liked, likeCount);
    }
}
