package com.example.olditemtradeplatform.product.controller;

import com.example.olditemtradeplatform.product.dto.ProductRequestDTO;
import com.example.olditemtradeplatform.product.dto.ProductResponseDTO;
import com.example.olditemtradeplatform.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id) {
        ProductResponseDTO product = productService.findProduct(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<ProductResponseDTO> createProduct(
            @PathVariable Long postId,
            @RequestBody @Valid ProductRequestDTO dto
    ) {
        ProductResponseDTO created = productService.createProduct(dto, postId);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
