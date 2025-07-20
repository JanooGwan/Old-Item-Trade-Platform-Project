package com.example.olditemtradeplatform.reportofpost.service;

import com.example.olditemtradeplatform.global.exception.CustomException;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPost;
import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPostId;
import com.example.olditemtradeplatform.reportofpost.dto.ReportOfPostRequestDTO;
import com.example.olditemtradeplatform.reportofpost.dto.ReportOfPostResponseDTO;
import com.example.olditemtradeplatform.reportofpost.exception.ReportOfPostErrorCode;
import com.example.olditemtradeplatform.reportofpost.repository.ReportOfPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportOfPostService {

    private final ReportOfPostRepository reportOfPostRepository;
    private final PostRepository postRepository;


    @Transactional(readOnly = true)
    public List<ReportOfPostResponseDTO> getReports() {
        return reportOfPostRepository.findAll().stream()
                .map(ReportOfPostResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReportOfPostResponseDTO findReport(Long postId, Long reporterId) {
        ReportOfPost report = reportOfPostRepository.findById(new ReportOfPostId(postId, reporterId))
                .orElseThrow(() -> new CustomException(ReportOfPostErrorCode.REPORT_NOT_FOUND));

        return ReportOfPostResponseDTO.from(report);
    }

    @Transactional
    public ReportOfPostResponseDTO reportPost(ReportOfPostRequestDTO dto, Member reporter) {
        Post post = postRepository.findById(dto.postId())
                .orElseThrow(() -> new CustomException(ReportOfPostErrorCode.POST_NOT_FOUND));

        ReportOfPostId id = new ReportOfPostId(post.getId(), reporter.getId());

        if (reportOfPostRepository.existsById(id)) {
            throw new CustomException(ReportOfPostErrorCode.DUPLICATE_REPORT);
        }

        ReportOfPost report = new ReportOfPost(post, reporter, dto.content());
        reportOfPostRepository.save(report);

        return ReportOfPostResponseDTO.from(report);
    }

    @Transactional
    public void deleteReport(Long postId, Long reporterId) {
        reportOfPostRepository.deleteById(new ReportOfPostId(postId, reporterId));
    }
}
