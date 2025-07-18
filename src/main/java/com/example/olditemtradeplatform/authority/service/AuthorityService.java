package com.example.olditemtradeplatform.authority.service;

import com.example.olditemtradeplatform.authority.dto.SuspendRequestDTO;
import com.example.olditemtradeplatform.authority.dto.SuspendedMemberResponseDTO;
import com.example.olditemtradeplatform.authority.exception.AuthorityErrorCode;
import com.example.olditemtradeplatform.global.exception.CustomException;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.domain.Role;
import com.example.olditemtradeplatform.member.dto.MemberResponseDTO;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPost;
import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPostId;
import com.example.olditemtradeplatform.reportofpost.repository.ReportOfPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ReportOfPostRepository reportRepository;

    @Transactional(readOnly = true)
    public List<SuspendedMemberResponseDTO> getSuspendedMembers() {
        return memberRepository.findAll().stream()
                .filter(Member::isSuspended)
                .map(SuspendedMemberResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDTO::from)
                .toList();
    }

    @Transactional
    public void suspendMember(SuspendRequestDTO requestDto) {
        ReportOfPostId reportId = new ReportOfPostId(requestDto.getPostId(), requestDto.getReporterId());
        ReportOfPost report = reportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException(AuthorityErrorCode.REPORT_NOT_FOUND));

        Post post = report.getPost();
        Member target = post.getWriter();

        target.updateSuspendInfo(true, requestDto.getSuspendUntil(), requestDto.getSuspendReason());
        memberRepository.save(target);
        postRepository.delete(post);
    }

    @Transactional
    public void changeRole(Long memberId, String newRole) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(AuthorityErrorCode.MEMBER_NOT_FOUND));

        Role roleEnum;
        try {
            roleEnum = Role.valueOf(newRole.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(AuthorityErrorCode.INVALID_ROLE);
        }

        member.updateRole(roleEnum);
        memberRepository.save(member);
    }

    @Transactional
    public void unsuspendMember(Long memberId) {
        Member target = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(AuthorityErrorCode.MEMBER_NOT_FOUND));

        target.updateSuspendInfo(false, null, null);
    }
}
