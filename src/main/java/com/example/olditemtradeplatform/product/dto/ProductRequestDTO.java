package com.example.olditemtradeplatform.product.dto;

import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.product.domain.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRequestDTO {

    @NotNull(message = "게시글 ID는 필수로 입력해야합니다.")
    private Long postId;

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @Positive(message = "가격은 0 이상이어야 합니다.")
    @NotNull(message = "수량은 필수로 입력해야 합니다.")
    private Long count;

    @Positive(message = "가격은 0 이상이어야 합니다.")
    @NotNull(message = "가격은 필수로 입력해야 합니다.")
    private Long price;

    public Product toEntity(Post post) {
        return Product.builder()
                .post(post)
                .name(name)
                .count(count)
                .price(price)
                .build();
    }
}
