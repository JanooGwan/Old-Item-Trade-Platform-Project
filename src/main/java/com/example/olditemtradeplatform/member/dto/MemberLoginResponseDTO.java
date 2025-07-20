package com.example.olditemtradeplatform.member.dto;

public record MemberLoginResponseDTO(
        Long id,
        String nickname,
        String role
) {
    public static MemberLoginResponseDTO of(Long id, String nickname, String role) {
        return new MemberLoginResponseDTO(id, nickname, role);
    }
}
