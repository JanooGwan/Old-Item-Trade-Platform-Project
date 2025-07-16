package com.example.olditemtradeplatform.reportofpost.dto;


import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPost;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportOfPostResponseDTO {

    private Long postId;
    private String title;
    private Long reporterId;
    private String reporterNickname;
    private String content;
    private LocalDateTime reportedDate;

    public static ReportOfPostResponseDTO from(ReportOfPost report) {
        return ReportOfPostResponseDTO.builder()
                .postId(report.getPost().getId())
                .title(report.getPost().getTitle())
                .reporterId(report.getReporter().getId())
                .reporterNickname(report.getReporter().getNickname())
                .content(report.getContent())
                .reportedDate(report.getReportedDate())
                .build();
    }
}
