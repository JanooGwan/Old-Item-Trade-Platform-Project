package com.example.olditemtradeplatform.member.dto;

import com.example.olditemtradeplatform.member.domain.Member;

import java.time.LocalDateTime;

public record MemberSearchResponseDTO(
        Long id,
        String userId,
        String nickname,
        String email,
        String role
) {
    public static MemberSearchResponseDTO from(Member member) {
        return new MemberSearchResponseDTO(
                member.getId(),
                member.getUserId(),
                member.getNickname(),
                member.getEmail(),
                member.getRole().name()
        );
    }
}
