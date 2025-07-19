package com.example.olditemtradeplatform.post.dto;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.BuyOrSale;
import com.example.olditemtradeplatform.post.domain.DealStatus;
import com.example.olditemtradeplatform.post.domain.DealWay;
import com.example.olditemtradeplatform.post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateRequestDTO {

    @Schema(description = "게시글 제목", example = "맥북 급처합니다", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "제목은 필수로 입력해야 합니다.")
    @Size(max = 100)
    private String title;

    @Schema(description = "게시글 내용", example = "한 달밖에 안 쓴 맥북 에어 M2 판매합니다. 상태 최상!", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "내용은 필수로 입력해야 합니다.")
    @Size(max = 10000, message = "내용은 10000자 이내로 입력해주세요.")
    private String content;

    @Schema(description = "게시글 유형 (BUY: 구매요청, SALE: 판매글)", example = "SALE", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "게시글 타입을 지정해주어야 합니다.")
    private BuyOrSale buyOrSale;

    @Schema(description = "거래 방식 (DIRECT: 직거래, DELIVERY: 비대면/택배)", example = "DIRECT", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "거래 방법을 지정해주어야 합니다.")
    private DealWay dealWay;

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
