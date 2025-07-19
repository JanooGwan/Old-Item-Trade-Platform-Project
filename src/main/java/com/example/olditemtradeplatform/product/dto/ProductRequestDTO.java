package com.example.olditemtradeplatform.product.dto;

import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.product.domain.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record ProductRequestDTO(

        @Schema(
                description = "상품이 등록될 게시글 ID",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "게시글 ID는 필수로 입력해야합니다.")
        Long postId,

        @Schema(
                description = "상품 이름",
                example = "아이폰 14 프로",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank
        @Size(min = 1, max = 100)
        String name,

        @Schema(
                description = "상품 수량",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Positive(message = "수량은 0 이상이어야 합니다.")
        @NotNull(message = "수량은 필수로 입력해야 합니다.")
        Long count,

        @Schema(
                description = "상품 가격 (원)",
                example = "1200000",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Positive(message = "가격은 0 이상이어야 합니다.")
        @NotNull(message = "가격은 필수로 입력해야 합니다.")
        Long price

) {
    public Product toEntity(Post post) {
        return Product.builder()
                .post(post)
                .name(name)
                .count(count)
                .price(price)
                .build();
    }
}
