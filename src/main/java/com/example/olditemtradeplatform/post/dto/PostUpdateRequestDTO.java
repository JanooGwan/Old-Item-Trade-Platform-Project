package com.example.olditemtradeplatform.post.dto;

import com.example.olditemtradeplatform.post.domain.BuyOrSale;
import com.example.olditemtradeplatform.post.domain.DealStatus;
import com.example.olditemtradeplatform.post.domain.DealWay;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUpdateRequestDTO {

    @Schema(
            description = "게시글 수정 내용",
            example = "가격 조정 가능, 직거래 선호합니다!",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "내용은 필수로 입력해야 합니다.")
    @Size(max = 10000, message = "내용은 10000자 이내로 입력해주세요.")
    private String content;

    @Schema(
            description = "게시글 유형 (BUY: 구매요청, SALE: 판매글)",
            example = "SALE",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "게시글 타입을 지정해주어야 합니다.")
    private BuyOrSale buyOrSale;

    @Schema(
            description = "거래 방식 (DIRECT: 직거래, DELIVERY: 비대면/택배)",
            example = "DIRECT",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "거래 방법을 지정해주어야 합니다.")
    private DealWay dealWay;

    @Schema(
            description = "거래 상태 (WAITING: 거래 대기, RESERVED: 예약 중, COMPLETED: 거래 완료)",
            example = "RESERVED",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "거래 상황을 지정해주어야 합니다.")
    private DealStatus dealStatus;
}
