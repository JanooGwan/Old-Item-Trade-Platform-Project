package com.example.olditemtradeplatform.post.dto;

import com.example.olditemtradeplatform.post.domain.Post;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponseDTO {

    String writerName;
    String title;
    String content;
    LocalDateTime createDate;
    LocalDateTime modifiedDate;

    public static PostResponseDTO from(Post post) {
        return PostResponseDTO.builder()
                .writerName(post.getWriter().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .createDate(post.getCreateDate())
                .modifiedDate(post.getModifiedDate())
                .build();
    }
}
