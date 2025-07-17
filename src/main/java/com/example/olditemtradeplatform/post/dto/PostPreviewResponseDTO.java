package com.example.olditemtradeplatform.post.dto;

import com.example.olditemtradeplatform.post.domain.Post;
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
    private String dealStatus;

    public static PostPreviewResponseDTO of(Post post, String thumbnailImageUrl) {
        return PostPreviewResponseDTO.builder()
                .postId(post.getId())
                .writerName(post.getWriter().getNickname())
                .title(post.getTitle())
                .createdDate(post.getCreateDate())
                .viewCount(post.getViewCount())
                .likeCount((long) post.getLikes().size())
                .buyOrSale(post.getBuyOrSale().name())
                .thumbnailImageUrl(thumbnailImageUrl)
                .dealStatus(post.getDealStatus().name())
                .build();
    }

    public static PostPreviewResponseDTO from(Post post) {
        return PostPreviewResponseDTO.builder()
                .postId(post.getId())
                .writerName(post.getWriter().getNickname())
                .title(post.getTitle())
                .createdDate(post.getCreateDate())
                .viewCount(post.getViewCount())
                .likeCount((long) post.getLikes().size())
                .buyOrSale(post.getBuyOrSale().name())
                .thumbnailImageUrl(post.getPostImages().isEmpty() ? null : post.getPostImages().get(0).getImageUrl())
                .dealStatus(post.getDealStatus().name())
                .build();
    }
}
