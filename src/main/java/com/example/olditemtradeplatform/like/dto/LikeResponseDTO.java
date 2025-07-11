package com.example.olditemtradeplatform.like.dto;

import com.example.olditemtradeplatform.like.domain.Like;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeResponseDTO {

    private Long postId;
    private Long memberId;
    private String memberNickname;

    public static LikeResponseDTO from(Like like) {
        return LikeResponseDTO.builder()
                .postId(like.getPost().getId())
                .memberId(like.getMember().getId())
                .memberNickname(like.getMember().getNickname())
                .build();
    }
}
