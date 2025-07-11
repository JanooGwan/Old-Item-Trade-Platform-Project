package com.example.olditemtradeplatform.postimage.dto;

import com.example.olditemtradeplatform.postimage.domain.PostImage;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostImageResponseDTO {

    private Long postId;
    private String imageUrl;

    public static PostImageResponseDTO from(PostImage postImage) {
        return PostImageResponseDTO.builder()
                .postId(postImage.getPost().getId())
                .imageUrl(postImage.getImageUrl())
                .build();
    }
}
