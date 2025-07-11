package com.example.olditemtradeplatform.post.dto;

import com.example.olditemtradeplatform.post.domain.DealStatus;
import com.example.olditemtradeplatform.post.domain.DealWay;
import com.example.olditemtradeplatform.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUpdateRequestDTO {

    @NotBlank
    String content;

    @NotNull
    DealWay dealWay;

    @NotNull
    DealStatus dealStatus;

    public Post toEntity() {
        return Post.builder()
                .content(this.content)
                .dealWay(this.dealWay)
                .dealStatus(this.dealStatus)
                .build();
    }
}
