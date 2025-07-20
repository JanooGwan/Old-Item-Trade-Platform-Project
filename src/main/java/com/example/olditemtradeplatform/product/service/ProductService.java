package com.example.olditemtradeplatform.product.service;

import com.example.olditemtradeplatform.global.exception.CustomException;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import com.example.olditemtradeplatform.product.domain.Product;
import com.example.olditemtradeplatform.product.dto.ProductRequestDTO;
import com.example.olditemtradeplatform.product.dto.ProductResponseDTO;
import com.example.olditemtradeplatform.product.exception.ProductErrorCode;
import com.example.olditemtradeplatform.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final PostRepository postRepository;

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO dto, Long postId) {
        Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new CustomException(ProductErrorCode.PRODUCT_NOT_FOUND));
        Product product = dto.toEntity(post);
        productRepository.save(product);
        return ProductResponseDTO.from(product);
    }


    @Transactional(readOnly = true)
    public ProductResponseDTO findProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ProductErrorCode.PRODUCT_NOT_FOUND));
        return ProductResponseDTO.from(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
