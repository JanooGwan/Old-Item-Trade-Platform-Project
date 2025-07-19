package com.example.olditemtradeplatform.product.dto;

import com.example.olditemtradeplatform.product.domain.Product;

public record ProductResponseDTO(
        Long productId,
        String name,
        Long count,
        Long price
) {
    public static ProductResponseDTO from(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getCount(),
                product.getPrice()
        );
    }
}
