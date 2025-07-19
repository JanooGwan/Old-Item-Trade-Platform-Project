package com.example.olditemtradeplatform.product.controller;

import com.example.olditemtradeplatform.product.dto.ProductRequestDTO;
import com.example.olditemtradeplatform.product.dto.ProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name = "상품 API", description = "게시글에 연결된 상품 정보 등록, 조회, 삭제 기능")
@RequestMapping("/api/products")
public interface ProductApi {

    @Operation(summary = "상품 등록", description = "게시글에 연결할 상품 정보를 등록합니다.")
    @ApiResponse(responseCode = "200", description = "상품 등록 성공", content = @Content(mediaType = "application/json"))
    @PostMapping
    ResponseEntity<ProductResponseDTO> createProduct(
            @RequestBody(
                    description = "상품 등록 요청 DTO",
                    required = true,
                    content = @Content(mediaType = "application/json")
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody ProductRequestDTO dto
    );

    @Operation(summary = "상품 조회", description = "상품 ID를 통해 상품 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "상품 조회 성공", content = @Content(mediaType = "application/json"))
    @GetMapping("/{id}")
    ResponseEntity<ProductResponseDTO> getProduct(
            @Parameter(description = "상품 ID", example = "1")
            @PathVariable Long id
    );

    @Operation(summary = "상품 삭제", description = "상품 ID를 기반으로 해당 상품을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "상품 삭제 성공")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(
            @Parameter(description = "상품 ID", example = "1")
            @PathVariable Long id
    );
}
