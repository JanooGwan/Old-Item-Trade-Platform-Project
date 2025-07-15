package com.example.olditemtradeplatform.like.dto;

import com.example.olditemtradeplatform.like.domain.Like;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LikeResponseDTO {

    private Long postId;
    private Long memberId;
    private boolean liked;
    private Long likeCount;

    public static LikeResponseDTO from(Like like) {
        return LikeResponseDTO.builder()
                .postId(like.getPost().getId())
                .memberId(like.getMember().getId())
                .liked(true)
                .likeCount(0L)
                .build();
    }

    public static LikeResponseDTO of(Like like, boolean liked, Long likeCount) {
        return LikeResponseDTO.builder()
                .postId(like.getPost().getId())
                .memberId(like.getMember().getId())
                .liked(liked)
                .likeCount(likeCount)
                .build();
    }

    public static LikeResponseDTO of(Long postId, Long memberId, boolean liked, Long likeCount) {
        return LikeResponseDTO.builder()
                .postId(postId)
                .memberId(memberId)
                .liked(liked)
                .likeCount(likeCount)
                .build();
    }
}
