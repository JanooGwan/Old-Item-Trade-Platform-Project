package com.example.olditemtradeplatform.post.controller;

import com.example.olditemtradeplatform.post.domain.DealStatus;
import com.example.olditemtradeplatform.post.dto.*;
import com.example.olditemtradeplatform.product.dto.ProductRequestDTO;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "게시글 API", description = "게시글 작성, 조회, 수정, 삭제 기능 제공")
@RequestMapping("/api/posts")
public interface PostApi {

    @Operation(summary = "게시글 단건 조회", description = "게시글 ID로 게시글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json"))
    @GetMapping("/{postId}")
    ResponseEntity<PostResponseDTO> getPost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "게시글 목록 조회", description = "전체 게시글 목록을 조회합니다. 거래 상태(dealStatus)로 필터링 가능합니다.")
    @ApiResponse(responseCode = "200", description = "목록 조회 성공", content = @Content(mediaType = "application/json"))
    @GetMapping
    ResponseEntity<List<PostPreviewResponseDTO>> getPosts(
            @Parameter(
                    description = "거래 상태 필터 (WAITING, RESERVED, COMPLETED)",
                    required = false,
                    in = ParameterIn.QUERY,
                    schema = @Schema(implementation = DealStatus.class)
            )
            @RequestParam(value = "dealStatus", required = false) DealStatus dealStatus
    );

    @PostMapping(consumes = {"multipart/form-data"})
    @Operation(summary = "게시글 생성", description = "게시글, 상품 정보, 이미지 파일을 포함한 게시글을 생성합니다. (multipart/form-data)")
    @ApiResponse(responseCode = "200", description = "생성 성공", content = @Content(mediaType = "application/json"))
    ResponseEntity<PostResponseDTO> createPost(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "게시글 작성 요청 DTO",
                    required = true,
                    content = @Content(mediaType = "application/json")
            )
            @Valid @RequestPart("post") PostCreateRequestDTO postRequestDto,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 정보 요청 DTO",
                    required = true,
                    content = @Content(mediaType = "application/json")
            )
            @Valid @RequestPart("product") ProductRequestDTO productRequestDto,

            @Parameter(description = "이미지 파일 리스트 (최대 5장)", required = false)
            @RequestPart(value = "images", required = false) List<MultipartFile> images,

            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "게시글 수정", description = "게시글 ID를 기반으로 게시글을 수정합니다. 작성자만 수정할 수 있습니다.")
    @ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(mediaType = "application/json"))
    @PutMapping("/{postId}")
    ResponseEntity<PostResponseDTO> updatePost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId,

            @RequestBody(
                    description = "게시글 수정 요청 DTO",
                    required = true,
                    content = @Content(mediaType = "application/json")
            )
            @org.springframework.web.bind.annotation.RequestBody PostUpdateRequestDTO dto,

            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "게시글 삭제", description = "게시글 ID를 기반으로 삭제합니다. 작성자만 삭제할 수 있습니다.")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @DeleteMapping("/{postId}")
    ResponseEntity<Void> deletePost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    );
}
