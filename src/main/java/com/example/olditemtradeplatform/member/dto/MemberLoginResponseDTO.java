package com.example.olditemtradeplatform.member.dto;

public record MemberLoginResponseDTO(
        Long id,
        String nickname,
        String role,
        String accessToken
) {
    public static MemberLoginResponseDTO of(Long id, String nickname, String role, String accessToken) {
        return new MemberLoginResponseDTO(id, nickname, role, accessToken);
    }
}
