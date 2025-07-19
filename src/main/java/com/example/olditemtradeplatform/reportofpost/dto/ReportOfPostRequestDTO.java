package com.example.olditemtradeplatform.reportofpost.dto;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPost;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ReportOfPostRequestDTO(

        @Schema(
                description = "신고할 게시글 ID",
                example = "42",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Positive
        @NotNull(message = "신고할 게시글 ID는 필수로 입력해야합니다.")
        Long postId,

        @Schema(
                description = "신고 사유 및 내용",
                example = "사기성 글로 의심됩니다. 연락 후 잠수를 탔습니다.",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Size(max = 10000)
        @NotBlank(message = "내용은 필수로 입력해야 합니다.")
        String content

) {
    public ReportOfPost toEntity(Post post, Member reporter) {
        return new ReportOfPost(post, reporter, this.content);
    }
}
