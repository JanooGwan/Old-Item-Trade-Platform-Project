package com.example.olditemtradeplatform.authority.service;

import com.example.olditemtradeplatform.authority.dto.SuspendRequestDTO;
import com.example.olditemtradeplatform.authority.dto.SuspendStatusResponseDTO;
import com.example.olditemtradeplatform.authority.exception.AuthorityErrorCode;
import com.example.olditemtradeplatform.global.exception.CustomException;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.domain.Role;
import com.example.olditemtradeplatform.member.dto.MemberResponseDTO;
import com.example.olditemtradeplatform.member.exception.MemberErrorCode;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<SuspendStatusResponseDTO> getSuspendedMembers() {
        return memberRepository.findAll().stream()
                .filter(Member::isSuspended)
                .map(SuspendStatusResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDTO::from)
                .toList();
    }

    @Transactional
    public SuspendStatusResponseDTO suspendMember(Long memberId, SuspendRequestDTO dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        member.suspendMember(dto.suspendUntil(), dto.suspendReason());
        return SuspendStatusResponseDTO.from(member);
    }

    @Transactional
    public SuspendStatusResponseDTO unsuspendMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(AuthorityErrorCode.MEMBER_NOT_FOUND));

        member.unsuspendMember();
        return SuspendStatusResponseDTO.from(member);
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
    public void forceWithdraw(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(AuthorityErrorCode.MEMBER_NOT_FOUND));
        memberRepository.delete(member);
    }
}
