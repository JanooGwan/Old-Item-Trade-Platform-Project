package com.example.olditemtradeplatform.member.dto;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.dto.PostPreviewInMypageResponseDTO;

import java.util.List;

public record MemberPageViewResponseDTO(
        String userId,
        String email,
        List<PostPreviewInMypageResponseDTO> posts
) {
    public static MemberPageViewResponseDTO of(Member member, List<PostPreviewInMypageResponseDTO> posts) {
        return new MemberPageViewResponseDTO(
                member.getUserId(),
                member.getEmail(),
                posts
        );
    }
}
