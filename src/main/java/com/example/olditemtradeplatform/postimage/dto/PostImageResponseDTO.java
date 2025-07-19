package com.example.olditemtradeplatform.postimage.dto;

import com.example.olditemtradeplatform.postimage.domain.PostImage;

public record PostImageResponseDTO(
        Long postId,
        String imageUrl
) {
    public static PostImageResponseDTO from(PostImage postImage) {
        return new PostImageResponseDTO(
                postImage.getPost().getId(),
                postImage.getImageUrl()
        );
    }
}
