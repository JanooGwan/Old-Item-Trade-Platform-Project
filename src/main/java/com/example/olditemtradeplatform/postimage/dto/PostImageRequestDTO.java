package com.example.olditemtradeplatform.postimage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

public record PostImageRequestDTO(

        @Schema(
                description = "이미지를 업로드할 게시글 ID",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Positive
        @NotNull(message = "게시글 ID는 필수입니다.")
        Long postId,

        @Schema(
                description = "업로드할 이미지 파일 (최대 5MB)",
                type = "string",
                format = "binary",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "이미지 파일은 필수입니다.")
        MultipartFile file

) {}
