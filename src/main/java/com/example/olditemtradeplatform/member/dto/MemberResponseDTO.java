package com.example.olditemtradeplatform.member.dto;

import com.example.olditemtradeplatform.member.domain.Member;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponseDTO {

    Long id;
    String userId;
    String nickname;
    String email;
    String role;
    boolean isSuspended;
    LocalDate suspendUntil;

    public static MemberResponseDTO from(Member member) {
        return MemberResponseDTO.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .role(member.getRole().toString())
                .isSuspended(member.isSuspended())
                .suspendUntil(member.getSuspendUntil())
                .build();
    }
}
