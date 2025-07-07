package com.example.olditemtradeplatform.dto.member;

import com.example.olditemtradeplatform.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDTO {

    private final Long id;
    private final String userId;
    private final String email;
    private final String nickname;

    public MemberResponseDTO(Member member) {
        this.id = member.getId();
        this.userId = member.getUserId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
    }
}
