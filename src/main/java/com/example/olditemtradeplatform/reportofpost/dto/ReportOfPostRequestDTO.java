package com.example.olditemtradeplatform.reportofpost.dto;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPost;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportOfPostRequestDTO {

    @NotNull(message = "신고할 게시글 ID는 필수로 입력해야합니다.")
    Long postId;

    @Size(max = 10000)
    @NotBlank(message = "내용은 필수로 입력해야 합니다.")
    String content;

    public ReportOfPost toEntity(Post post, Member reporter) {
        return new ReportOfPost(post, reporter, this.content);
    }
}