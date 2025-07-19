package com.example.olditemtradeplatform.reportofpost.controller;

import com.example.olditemtradeplatform.reportofpost.dto.ReportOfPostRequestDTO;
import com.example.olditemtradeplatform.reportofpost.dto.ReportOfPostResponseDTO;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name = "게시글 신고 API", description = "게시글 신고 등록 기능 제공")
@RequestMapping("/api/post-reports")
public interface ReportOfPostApi {

    @Operation(summary = "게시글 신고 등록", description = "게시글에 대해 신고를 등록합니다.")
    @ApiResponse(responseCode = "200", description = "신고 등록 성공", content = @Content(mediaType = "application/json"))
    @PostMapping
    ResponseEntity<ReportOfPostResponseDTO> reportPost(
            @RequestBody(
                    description = "게시글 신고 요청 DTO",
                    required = true,
                    content = @Content(mediaType = "application/json")
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody ReportOfPostRequestDTO dto,

            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    );
}
