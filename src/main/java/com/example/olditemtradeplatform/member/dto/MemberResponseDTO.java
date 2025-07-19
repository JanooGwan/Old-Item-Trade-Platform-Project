package com.example.olditemtradeplatform.member.dto;

import com.example.olditemtradeplatform.member.domain.Member;

import java.time.LocalDate;

public record MemberResponseDTO(
        Long id,
        String userId,
        String nickname,
        String email,
        String role,
        boolean isSuspended,
        LocalDate suspendUntil
) {
    public static MemberResponseDTO from(Member member) {
        return new MemberResponseDTO(
                member.getId(),
                member.getUserId(),
                member.getNickname(),
                member.getEmail(),
                member.getRole().toString(),
                member.isSuspended(),
                member.getSuspendUntil()
        );
    }
}
