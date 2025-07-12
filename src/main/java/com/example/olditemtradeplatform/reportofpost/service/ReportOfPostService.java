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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportOfPostService {

    private final ReportOfPostRepository reportOfPostRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public ReportOfPostResponseDTO create(Long postId, Long reporterId, ReportOfPostRequestDTO dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        Member reporter = memberRepository.findById(reporterId)
                .orElseThrow(() -> new IllegalArgumentException("Reporter not found"));

        ReportOfPost report = dto.toEntity(post, reporter);
        reportOfPostRepository.save(report);

        return ReportOfPostResponseDTO.from(report);
    }

    @Transactional(readOnly = true)
    public ReportOfPostResponseDTO find(Long postId, Long reporterId) {
        ReportOfPost report = reportOfPostRepository.findById(new ReportOfPostId(postId, reporterId))
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));
        return ReportOfPostResponseDTO.from(report);
    }

    @Transactional
    public void delete(Long postId, Long reporterId) {
        reportOfPostRepository.deleteById(new ReportOfPostId(postId, reporterId));
    }
}
