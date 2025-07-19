package com.example.olditemtradeplatform.postimage.controller;

import com.example.olditemtradeplatform.postimage.dto.PostImageRequestDTO;
import com.example.olditemtradeplatform.postimage.dto.PostImageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시글 이미지 API", description = "게시글 이미지 업로드, 조회, 삭제 기능")
@RequestMapping("/api/post-images")
public interface PostImageApi {

    @Operation(
            summary = "게시글 이미지 업로드",
            description = "게시글 이미지 파일을 업로드합니다. 이미지에는 게시글 ID와 imageAt 번호가 포함됩니다."
    )
    @ApiResponse(responseCode = "200", description = "이미지 업로드 성공", content = @Content(mediaType = "application/json"))
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<PostImageResponseDTO> uploadPostImage(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "이미지 업로드 요청 DTO",
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @ModelAttribute PostImageRequestDTO dto
    );

    @Operation(
            summary = "게시글 이미지 조회",
            description = "게시글 ID와 imageAt 값을 통해 특정 이미지를 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "이미지 조회 성공", content = @Content(mediaType = "application/json"))
    @GetMapping("/{postId}/{imageAt}")
    ResponseEntity<PostImageResponseDTO> getImage(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId,

            @Parameter(description = "이미지 순서 번호", example = "1")
            @PathVariable Long imageAt
    );

    @Operation(
            summary = "게시글 이미지 삭제",
            description = "게시글 ID와 imageAt 값을 통해 특정 이미지를 삭제합니다."
    )
    @ApiResponse(responseCode = "204", description = "이미지 삭제 성공")
    @DeleteMapping("/{postId}/{imageAt}")
    ResponseEntity<Void> deleteImage(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId,

            @Parameter(description = "이미지 순서 번호", example = "1")
            @PathVariable Long imageAt
    );
}
