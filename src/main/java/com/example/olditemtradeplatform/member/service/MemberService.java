// MemberService.java (확장된 서비스 로직 포함)
package com.example.olditemtradeplatform.member.service;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.domain.Role;
import com.example.olditemtradeplatform.member.dto.MemberRegisterRequestDTO;
import com.example.olditemtradeplatform.member.dto.MemberResponseDTO;
import com.example.olditemtradeplatform.member.dto.MemberUpdateRequestDTO;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import com.example.olditemtradeplatform.post.dto.PostPreviewResponseDTO;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import com.example.olditemtradeplatform.like.repository.LikeRepository;
import com.example.olditemtradeplatform.reportofpost.dto.ReportOfPostResponseDTO;
import com.example.olditemtradeplatform.reportofpost.repository.ReportOfPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final ReportOfPostRepository reportRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponseDTO register(MemberRegisterRequestDTO dto, String encodedPassword) {
        Member member = dto.toEntity(encodedPassword);
        memberRepository.save(member);
        return MemberResponseDTO.from(member);
    }

    @Transactional(readOnly = true)
    public MemberResponseDTO findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return MemberResponseDTO.from(member);
    }

    public Optional<Member> findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }

    @Transactional
    public MemberResponseDTO updateMember(Member member, MemberUpdateRequestDTO dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        member.updateMember(encodedPassword, dto.getEmail(), dto.getNickname());
        return MemberResponseDTO.from(member);
    }

    @Transactional
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponseDTO> getMyPosts(Member member) {
        return postRepository.findByWriter(member).stream()
                .map(PostPreviewResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostPreviewResponseDTO> getLikedPosts(Member member) {
        return likeRepository.findLikedPostsByMember(member.getId()).stream()
                .map(PostPreviewResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReportOfPostResponseDTO> getReportList(Member member) {
        if (!(member.getRole() == Role.MANAGER || member.getRole() == Role.ADMIN)) {
            throw new SecurityException("권한이 없습니다");
        }
        return reportRepository.findAll().stream()
                .map(ReportOfPostResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDTO> getAllMembers(Member member) {
        if (!(member.getRole() == Role.ADMIN)) {
            throw new SecurityException("권한이 없습니다");
        }
        return memberRepository.findAll().stream()
                .map(MemberResponseDTO::from)
                .collect(Collectors.toList());
    }
} 
