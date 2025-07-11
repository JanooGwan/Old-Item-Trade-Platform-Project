package com.example.olditemtradeplatform.post.dto;

import com.example.olditemtradeplatform.post.domain.BuyOrSale;
import com.example.olditemtradeplatform.post.domain.DealStatus;
import com.example.olditemtradeplatform.post.domain.DealWay;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateRequestDTO {

    @NotBlank
    @Size(max = 100)
    String title;

    @NotBlank
    String content;

    @NotNull
    BuyOrSale buyOrSale;

    @NotNull
    DealWay dealWay;

    public Post toEntity(Member writer) {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .writer(writer)
                .buyOrSale(this.buyOrSale)
                .viewCount(0L)
                .likeCount(0L)
                .dealWay(this.dealWay)
                .dealStatus(DealStatus.WAITING)
                .build();
    }
}
