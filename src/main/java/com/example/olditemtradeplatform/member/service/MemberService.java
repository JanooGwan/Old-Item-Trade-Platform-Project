package com.example.olditemtradeplatform.member.service;

import com.example.olditemtradeplatform.global.exception.CustomException;
import com.example.olditemtradeplatform.like.domain.Like;
import com.example.olditemtradeplatform.like.repository.LikeRepository;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.domain.Role;
import com.example.olditemtradeplatform.member.dto.*;
import com.example.olditemtradeplatform.member.exception.MemberErrorCode;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.dto.PostPreviewInMypageResponseDTO;
import com.example.olditemtradeplatform.post.dto.PostPreviewResponseDTO;
import com.example.olditemtradeplatform.post.repository.PostRepository;
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
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));
        return MemberResponseDTO.from(member);
    }

    public Optional<Member> findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }

    @Transactional
    public MemberResponseDTO signup(MemberRegisterRequestDTO dto) {
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            throw new CustomException(MemberErrorCode.PASSWORD_CONFIRM_MISMATCH);
        }

        if (memberRepository.existsByUserId(dto.getUserId())) {
            throw new CustomException(MemberErrorCode.DUPLICATE_USER_ID);
        }

        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException(MemberErrorCode.DUPLICATE_EMAIL);
        }

        if (memberRepository.existsByNickname(dto.getNickname())) {
            throw new CustomException(MemberErrorCode.DUPLICATE_NICKNAME);
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Member member = dto.toEntity(encodedPassword);
        memberRepository.save(member);

        return MemberResponseDTO.from(member);
    }



    @Transactional
    public MemberResponseDTO updateMember(Member member, MemberUpdateRequestDTO dto) {
        Member persistentMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), persistentMember.getPassword())) {
            throw new CustomException(MemberErrorCode.INVALID_CURRENT_PASSWORD);
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new CustomException(MemberErrorCode.PASSWORD_CONFIRM_MISMATCH);
        }

        if (memberRepository.existsByEmailAndIdNot(dto.getEmail(), member.getId())) {
            throw new CustomException(MemberErrorCode.DUPLICATE_EMAIL);
        }

        if (memberRepository.existsByNicknameAndIdNot(dto.getNickname(), member.getId())) {
            throw new CustomException(MemberErrorCode.DUPLICATE_NICKNAME);
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
                .orElseThrow(() -> new CustomException(MemberErrorCode.NICKNAME_NOT_FOUND));

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
            throw new CustomException(MemberErrorCode.ACCESS_DENIED);
        }
        return reportRepository.findAll().stream()
                .map(ReportOfPostResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDTO> getAllMembers(Member member) {
        if (member.getRole() != Role.ADMIN) {
            throw new CustomException(MemberErrorCode.ACCESS_DENIED);
        }
        return memberRepository.findAll().stream()
                .map(MemberResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        List<Like> likes = likeRepository.findByMember(member);

        for (Like like : likes) {
            Post post = like.getPost();
            post.decreaseLikeCount();
        }

        memberRepository.delete(member);
    }
}
