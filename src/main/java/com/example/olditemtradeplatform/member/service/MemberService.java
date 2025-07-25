package com.example.olditemtradeplatform.member.service;

import com.example.olditemtradeplatform.global.exception.CustomException;
import com.example.olditemtradeplatform.like.domain.Like;
import com.example.olditemtradeplatform.like.repository.LikeRepository;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.dto.*;
import com.example.olditemtradeplatform.member.exception.MemberErrorCode;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.dto.PostPreviewInMypageResponseDTO;
import com.example.olditemtradeplatform.post.dto.PostPreviewResponseDTO;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public MemberResponseDTO findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberResponseDTO.from(member);
    }

    @Transactional(readOnly = true)
    public MemberResponseDTO findByUserId(String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberResponseDTO.from(member);
    }

    @Transactional
    public MemberResponseDTO signup(MemberRegisterRequestDTO dto) {
        if (!dto.password().equals(dto.passwordConfirm())) {
            throw new CustomException(MemberErrorCode.PASSWORD_CONFIRM_MISMATCH);
        }

        if (memberRepository.existsByUserId(dto.userId())) {
            throw new CustomException(MemberErrorCode.DUPLICATE_USER_ID);
        }

        if (memberRepository.existsByEmail(dto.email())) {
            throw new CustomException(MemberErrorCode.DUPLICATE_EMAIL);
        }

        if (memberRepository.existsByNickname(dto.nickname())) {
            throw new CustomException(MemberErrorCode.DUPLICATE_NICKNAME);
        }

        String encodedPassword = passwordEncoder.encode(dto.password());
        Member member = dto.toEntity(encodedPassword);
        memberRepository.save(member);

        return MemberResponseDTO.from(member);
    }

    @Transactional
    public MemberResponseDTO updateMember(Member member, MemberUpdateRequestDTO dto) {
        Member persistentMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.currentPassword(), persistentMember.getPassword())) {
            throw new CustomException(MemberErrorCode.INVALID_CURRENT_PASSWORD);
        }

        if (!dto.newPassword().equals(dto.confirmPassword())) {
            throw new CustomException(MemberErrorCode.PASSWORD_CONFIRM_MISMATCH);
        }

        if (memberRepository.existsByEmailAndIdNot(dto.email(), member.getId())) {
            throw new CustomException(MemberErrorCode.DUPLICATE_EMAIL);
        }

        if (memberRepository.existsByNicknameAndIdNot(dto.nickname(), member.getId())) {
            throw new CustomException(MemberErrorCode.DUPLICATE_NICKNAME);
        }

        String encodedPassword = passwordEncoder.encode(dto.newPassword());
        persistentMember.updateMember(encodedPassword, dto.email(), dto.nickname());

        updateAuthentication(persistentMember);

        return MemberResponseDTO.from(persistentMember);
    }

    private void updateAuthentication(Member updatedMember) {
        CustomUserDetails updatedUserDetails = new CustomUserDetails(updatedMember);

        UsernamePasswordAuthenticationToken newAuth =
                new UsernamePasswordAuthenticationToken(
                        updatedUserDetails,
                        null,
                        updatedUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(newAuth);
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
