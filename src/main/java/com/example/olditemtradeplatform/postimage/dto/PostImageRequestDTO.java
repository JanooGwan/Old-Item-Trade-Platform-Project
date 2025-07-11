package com.example.olditemtradeplatform.postimage.dto;

import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.postimage.domain.PostImage;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostImageRequestDTO {

    private Long imageAt;
    private String imageUrl;

    public PostImage toEntity(Post post) {
        return new PostImage(post, imageAt, imageUrl);
    }
}
