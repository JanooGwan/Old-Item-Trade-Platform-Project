package com.example.olditemtradeplatform.like.dto;

import com.example.olditemtradeplatform.like.domain.Like;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.Post;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeRequestDTO {

    public Like toEntity(Post post, Member member) {
        return new Like(post, member);
    }
}
