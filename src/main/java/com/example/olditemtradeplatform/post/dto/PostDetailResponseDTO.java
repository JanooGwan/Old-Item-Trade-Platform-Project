package com.example.olditemtradeplatform.post.dto;

import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.postimage.domain.PostImage;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostDetailResponseDTO {

    Long id;
    String writerName;
    String title;
    String content;
    LocalDateTime createDate;
    LocalDateTime modifiedDate;
    Long viewCount;
    Long likeCount;

    String buyOrSale;
    String dealStatus;
    String dealWay;
    Long price;
    Long count;
    List<String> images;

    boolean isAuthor;
    boolean isLiked;


    public static PostDetailResponseDTO from(Post post) {
        return from(post, false, false);
    }

    public static PostDetailResponseDTO from(Post post, boolean isAuthor, boolean isLiked) {
        return PostDetailResponseDTO.builder()
                .id(post.getId())
                .writerName(post.getWriter().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .createDate(post.getCreateDate())
                .modifiedDate(post.getModifiedDate())

                .buyOrSale(post.getBuyOrSale().name())
                .dealWay(post.getDealWay().name())
                .dealStatus(post.getDealStatus().name())
                .price(post.getProducts().isEmpty() ? null : post.getProducts().get(0).getPrice())
                .count(post.getProducts().isEmpty() ? null : post.getProducts().get(0).getCount())
                .images(post.getPostImages().stream()
                        .map(PostImage::getImageUrl)
                        .toList())

                .isAuthor(isAuthor)
                .isLiked(isLiked)
                .build();
    }
}
