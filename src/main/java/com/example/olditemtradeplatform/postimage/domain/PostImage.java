package com.example.olditemtradeplatform.postimage.domain;

import com.example.olditemtradeplatform.post.domain.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage {

    @EmbeddedId
    private PostImageId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "image_url", nullable = false, unique = true)
    private String imageUrl;


    public PostImage(Post post, Long imageAt, String imageUrl) {
        this.post = post;
        this.id = new PostImageId(post.getId(), imageAt);
        this.imageUrl = imageUrl;
    }
}
