package com.example.olditemtradeplatform.like.dto;

import com.example.olditemtradeplatform.like.domain.Like;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.Post;

public record LikeRequestDTO(
        Long postId,
        Long memberId
) {
    public Like toEntity(Post post, Member member) {
        return new Like(post, member);
    }
}
