package com.example.olditemtradeplatform.reportofpost.dto;

import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.product.domain.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    private Long count;

    @NotNull
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
