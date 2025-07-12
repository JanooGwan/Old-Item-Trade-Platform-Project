package com.example.olditemtradeplatform.post.dto;

import com.example.olditemtradeplatform.post.domain.BuyOrSale;
import com.example.olditemtradeplatform.post.domain.DealStatus;
import com.example.olditemtradeplatform.post.domain.DealWay;
import com.example.olditemtradeplatform.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUpdateRequestDTO {

    @NotBlank(message = "내용은 필수로 입력해야 합니다.")
    @Size(max = 10000, message = "내용은 10000자 이내로 입력해주세요.")
    String content;

    @NotNull(message = "게시글 타입을 지정해주어야 합니다.")
    BuyOrSale buyOrSale;

    @NotNull(message = "거래 방법을 지정해주어야 합니다.")
    DealWay dealWay;

    @NotNull(message = "거래 상황을 지정해주어야 합니다.")
    DealStatus dealStatus;

    public Post toEntity() {
        return Post.builder()
                .content(this.content)
                .dealWay(this.dealWay)
                .dealStatus(this.dealStatus)
                .build();
    }
}
