package com.example.olditemtradeplatform.like.controller;

import com.example.olditemtradeplatform.like.dto.LikeResponseDTO;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "좋아요 API", description = "게시글 좋아요 기능 관련 API")
@RequestMapping("/likes")
public interface LikeApi {

    @Operation(summary = "좋아요 등록 또는 해제", description = "해당 게시글에 대해 좋아요를 토글합니다. (좋아요 안 되어 있으면 등록, 되어 있으면 취소)")
    @ApiResponse(responseCode = "200", description = "좋아요 토글 성공", content = @Content(mediaType = "application/json"))
    @PostMapping("/{postId}")
    ResponseEntity<LikeResponseDTO> toggleLike(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "게시글 좋아요 수 조회", description = "해당 게시글의 총 좋아요 개수를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "좋아요 수 조회 성공", content = @Content(mediaType = "application/json"))
    @GetMapping("/{postId}/count")
    ResponseEntity<Integer> countLikes(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId
    );

    @Operation(summary = "내가 좋아요 눌렀는지 확인", description = "현재 로그인한 사용자가 해당 게시글에 좋아요를 눌렀는지 여부를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "좋아요 여부 조회 성공", content = @Content(mediaType = "application/json"))
    @GetMapping("/{postId}/liked")
    ResponseEntity<Boolean> isLikedByUser(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    );
}
