package com.example.olditemtradeplatform.member.dto;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.dto.PostPreviewInMypageResponseDTO;
import com.example.olditemtradeplatform.post.dto.PostPreviewResponseDTO;
import lombok.*;

import java.util.List;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberPageViewResponseDTO {
    private String userId;
    private String email;
    private List<PostPreviewInMypageResponseDTO> posts;

    public static MemberPageViewResponseDTO of(Member member, List<PostPreviewInMypageResponseDTO> posts) {
        return MemberPageViewResponseDTO.builder()
                .userId(member.getUserId())
                .email(member.getEmail())
                .posts(posts)
                .build();
    }
}
