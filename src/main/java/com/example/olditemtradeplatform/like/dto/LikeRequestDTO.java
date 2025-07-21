package com.example.olditemtradeplatform.like.dto;

import com.example.olditemtradeplatform.like.domain.Like;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LikeRequestDTO(
        @Schema(description = "게시글 ID", example = "1")
        @NotNull(message = "postId는 필수로 입력해야 합니다.")
        @Positive(message = "postId는 양수여야 합니다.")
        Long postId,

        @Schema(description = "회원 ID", example = "10")
        @NotNull(message = "memberId는 필수로 입력해야 합니다.")
        @Positive(message = "memberId는 양수여야 합니다.")
        Long memberId
) {
    public Like toEntity(Post post, Member member) {
        return new Like(post, member);
    }
}
