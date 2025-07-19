package com.example.olditemtradeplatform.reportofpost.dto;

import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPost;

import java.time.LocalDateTime;

public record ReportOfPostResponseDTO(
        Long postId,
        String title,
        Long reporterId,
        String reporterNickname,
        String content,
        LocalDateTime reportedDate
) {
    public static ReportOfPostResponseDTO from(ReportOfPost report) {
        return new ReportOfPostResponseDTO(
                report.getPost().getId(),
                report.getPost().getTitle(),
                report.getReporter().getId(),
                report.getReporter().getNickname(),
                report.getContent(),
                report.getReportedDate()
        );
    }
}
