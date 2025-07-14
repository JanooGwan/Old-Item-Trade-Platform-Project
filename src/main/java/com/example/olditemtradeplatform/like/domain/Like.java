package com.example.olditemtradeplatform.like.domain;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "post_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @EmbeddedId
    LikeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id", nullable = false)
    Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    @JoinColumn(name = "member_id", nullable = false)
    Member member;

    public Like(Post post, Member member) {
        this.post = post;
        this.member = member;
    }
}
