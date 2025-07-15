package com.example.olditemtradeplatform.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PostPreviewResponseDTO {

    private Long postId;
    private String writerName;
    private String title;
    private LocalDateTime createdDate;
    private Long viewCount;
    private Long likeCount;
    private String buyOrSale;
    private String thumbnailImageUrl;
}