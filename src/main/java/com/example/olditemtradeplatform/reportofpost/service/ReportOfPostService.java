package com.example.olditemtradeplatform.reportofpost.service;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPost;
import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPostId;
import com.example.olditemtradeplatform.reportofpost.dto.ReportOfPostRequestDTO;
import com.example.olditemtradeplatform.reportofpost.dto.ReportOfPostResponseDTO;
import com.example.olditemtradeplatform.reportofpost.repository.ReportOfPostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportOfPostService {

    private final ReportOfPostRepository reportOfPostRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    @Transactional
    public ReportOfPostResponseDTO reportPost(ReportOfPostRequestDTO dto, Member reporter) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없습니다."));

        ReportOfPostId id = new ReportOfPostId(post.getId(), reporter.getId());

        if (reportOfPostRepository.existsById(id)) {
            throw new IllegalStateException("이미 이 게시글을 신고했습니다.");
        }

        ReportOfPost report = new ReportOfPost(post, reporter, dto.getContent());
        reportOfPostRepository.save(report);

        return ReportOfPostResponseDTO.from(report);
    }

    @Transactional(readOnly = true)
    public List<ReportOfPostResponseDTO> getReports() {
        return reportOfPostRepository.findAll().stream()
                .map(ReportOfPostResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReportOfPostResponseDTO findReport(Long postId, Long reporterId) {
        ReportOfPost report = reportOfPostRepository.findById(new ReportOfPostId(postId, reporterId))
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));
        return ReportOfPostResponseDTO.from(report);
    }

    @Transactional
    public void deleteReport(Long postId, Long reporterId) {
        reportOfPostRepository.deleteById(new ReportOfPostId(postId, reporterId));
    }
}
