package com.example.olditemtradeplatform.reportofpost.dto;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPost;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportOfPostRequestDTO {

    @NotBlank
    private String content;

    public ReportOfPost toEntity(Post post, Member reporter) {
        return new ReportOfPost(post, reporter, this.content);
    }
}