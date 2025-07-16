package com.example.olditemtradeplatform.authority.dto;

import com.example.olditemtradeplatform.member.domain.Member;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SuspendedMemberResponseDTO {

    private Long userId;
    private String nickName;
    private LocalDate suspendUntil;
    private String suspendReason;

    public static SuspendedMemberResponseDTO from(Member member) {
        return SuspendedMemberResponseDTO.builder()
                .userId(member.getId())
                .nickName(member.getNickname())
                .suspendUntil(member.getSuspendUntil())
                .suspendReason(member.getSuspendReason())
                .build();
    }
}
