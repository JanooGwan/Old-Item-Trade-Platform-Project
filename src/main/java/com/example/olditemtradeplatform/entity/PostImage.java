package com.example.olditemtradeplatform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage {

    @EmbeddedId
    private PostImageId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @NotBlank
    @Column(name = "image_url", nullable = false)
    private String imageUrl;


    public PostImage(Post post, Long imageAt, String imageUrl) {
        this.post = post;
        this.id = new PostImageId(post.getId(), imageAt);
        this.imageUrl = imageUrl;
    }
}
