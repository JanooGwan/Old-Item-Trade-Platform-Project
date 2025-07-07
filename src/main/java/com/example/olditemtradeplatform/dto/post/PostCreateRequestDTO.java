package com.example.olditemtradeplatform.dto.post;

import com.example.olditemtradeplatform.entity.DealStatus;
import com.example.olditemtradeplatform.entity.Member;
import com.example.olditemtradeplatform.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequestDTO {

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    private String content;

    private DealStatus dealStatus;

    public Post toEntity(Member writer) {
        return Post.builder()
                .writer(writer)
                .title(title)
                .dealStatus(DealStatus.IN_PROGRESS)
                .content(content)
                .build();
    }
}
