package com.example.olditemtradeplatform.post.dto;

import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.postimage.domain.PostImage;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDTO(
        Long id,
        String writerName,
        String title,
        String content,
        LocalDateTime createDate,
        LocalDateTime modifiedDate,
        Long viewCount,
        Long likeCount,
        String buyOrSale,
        String dealStatus,
        String dealWay,
        Long price,
        Long count,
        List<String> images,
        boolean isAuthor,
        boolean isLiked
) {
    public static PostResponseDTO from(Post post) {
        return of(post, false, false);
    }

    public static PostResponseDTO of(Post post, boolean isAuthor, boolean isLiked) {
        return new PostResponseDTO(
                post.getId(),
                post.getWriter().getNickname(),
                post.getTitle(),
                post.getContent(),
                post.getCreateDate(),
                post.getModifiedDate(),
                post.getViewCount(),
                post.getLikeCount(),
                post.getBuyOrSale().name(),
                post.getDealStatus().name(),
                post.getDealWay().name(),
                post.getProducts().isEmpty() ? null : post.getProducts().get(0).getPrice(),
                post.getProducts().isEmpty() ? null : post.getProducts().get(0).getCount(),
                post.getPostImages().stream()
                        .map(PostImage::getImageUrl)
                        .toList(),
                isAuthor,
                isLiked
        );
    }
}
