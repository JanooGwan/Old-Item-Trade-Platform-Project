package com.example.olditemtradeplatform.member.dto;

import com.example.olditemtradeplatform.member.domain.Member;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberSearchResponseDTO {

    Long id;
    String userId;
    String nickname;
    String email;
    String role;
    LocalDateTime createdAt;

    public static MemberSearchResponseDTO from(Member member) {
        return MemberSearchResponseDTO.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .role(member.getRole().toString())
                .build();
    }
}
