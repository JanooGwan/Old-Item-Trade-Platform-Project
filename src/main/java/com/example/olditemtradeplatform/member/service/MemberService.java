package com.example.olditemtradeplatform.member.service;

import com.example.olditemtradeplatform.like.domain.Like;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.domain.Role;
import com.example.olditemtradeplatform.member.dto.MemberPageViewResponseDTO;
import com.example.olditemtradeplatform.member.dto.MemberRegisterRequestDTO;
import com.example.olditemtradeplatform.member.dto.MemberResponseDTO;
import com.example.olditemtradeplatform.member.dto.MemberUpdateRequestDTO;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.dto.PostPreviewInMypageResponseDTO;
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
    public MemberResponseDTO register(MemberRegisterRequestDTO dto, String encodedPassword) {
        if (memberRepository.existsByUserId(dto.getUserId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (memberRepository.existsByNickname(dto.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
        Member member = dto.toEntity(encodedPassword);
        memberRepository.save(member);
        return MemberResponseDTO.from(member);
    }

    @Transactional
    public MemberResponseDTO updateMember(Member member, MemberUpdateRequestDTO dto) {
        Member persistentMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), persistentMember.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        if (memberRepository.existsByEmailAndIdNot(dto.getEmail(), member.getId())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (memberRepository.existsByNicknameAndIdNot(dto.getNickname(), member.getId())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        String encodedPassword = passwordEncoder.encode(dto.getNewPassword());
        persistentMember.updateMember(encodedPassword, dto.getEmail(), dto.getNickname());

        return MemberResponseDTO.from(persistentMember);
    }

    @Transactional(readOnly = true)
    public List<PostPreviewInMypageResponseDTO> getMyPosts(Member member) {
        return postRepository.findByWriter(member).stream()
                .map(PostPreviewInMypageResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public MemberPageViewResponseDTO getOtherMemberPage(String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 닉네임의 회원이 존재하지 않습니다."));

        List<PostPreviewInMypageResponseDTO> posts = postRepository.findByWriter(member).stream()
                .map(PostPreviewInMypageResponseDTO::from)
                .toList();

        return MemberPageViewResponseDTO.of(member, posts);
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

    @Transactional
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        List<Like> likes = likeRepository.findByMember(member);

        for (Like like : likes) {
            Post post = like.getPost();
            post.decreaseLikeCount();
        }

        memberRepository.delete(member);
    }

} 
