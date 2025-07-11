package com.example.olditemtradeplatform.product.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponseDTO {

    private Long productId;
    private String name;
    private Long count;
    private Long price;

    public static ProductResponseDTO from(Product product) {
        return ProductResponseDTO.builder()
                .productId(product.getId())
                .name(product.getName())
                .count(product.getCount())
                .price(product.getPrice())
                .build();
    }
}
