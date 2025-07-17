package com.example.olditemtradeplatform.post.dto;

import com.example.olditemtradeplatform.post.domain.Post;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PostPreviewInMypageResponseDTO {
    private Long postId;
    private String title;
    private Long likeCount;
    private Long viewCount;

    public static PostPreviewInMypageResponseDTO from(Post post) {
        return PostPreviewInMypageResponseDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .likeCount(post.getLikeCount())
                .viewCount(post.getViewCount())
                .build();
    }
}