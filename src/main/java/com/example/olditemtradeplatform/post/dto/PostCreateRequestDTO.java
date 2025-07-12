package com.example.olditemtradeplatform.post.dto;

import com.example.olditemtradeplatform.post.domain.BuyOrSale;
import com.example.olditemtradeplatform.post.domain.DealStatus;
import com.example.olditemtradeplatform.post.domain.DealWay;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.Post;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateRequestDTO {

    @NotBlank(message = "제목은 필수로 입력해야 합니다.")
    @Size(max = 100)
    String title;

    @NotBlank(message = "내용은 필수로 입력해야 합니다.")
    @Size(max = 10000, message = "내용은 10000자 이내로 입력해주세요.")
    String content;

    @NotNull(message = "게시글 타입을 지정해주어야 합니다.")
    BuyOrSale buyOrSale;

    @NotNull(message = "거래 방법을 지정해주어야 합니다.")
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
