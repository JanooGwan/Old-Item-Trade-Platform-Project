package com.example.olditemtradeplatform.member.dto;

import lombok.*;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberLoginResponseDTO {

    private Long id;
    private String nickname;
    private String role;
    private String accessToken;

    public static MemberLoginResponseDTO of(Long id, String nickname, String role, String accessToken) {
        return MemberLoginResponseDTO.builder()
                .id(id)
                .nickname(nickname)
                .role(role)
                .accessToken(accessToken)
                .build();
    }
}
