package com.example.olditemtradeplatform.authority.service;

import com.example.olditemtradeplatform.authority.dto.SuspendRequestDTO;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPost;
import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPostId;
import com.example.olditemtradeplatform.reportofpost.repository.ReportOfPostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ReportOfPostRepository reportRepository;

    @Transactional
    public void suspendMember(SuspendRequestDTO requestDto) {
        ReportOfPostId reportId = new ReportOfPostId(requestDto.getPostId(), requestDto.getReporterId());
        ReportOfPost report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("신고 내역이 존재하지 않습니다."));

        Post post = report.getPost();
        Member target = post.getWriter();

        target.updateSuspendInfo(true, requestDto.getSuspendUntil());
        memberRepository.save(target);

        postRepository.delete(post);
    }

    @Transactional
    public void unsuspendMember(Long memberId) {
        Member target = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        target.updateSuspendInfo(false, null);
        memberRepository.save(target);
    }
}
