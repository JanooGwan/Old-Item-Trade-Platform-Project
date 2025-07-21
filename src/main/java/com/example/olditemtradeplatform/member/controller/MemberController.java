package com.example.olditemtradeplatform.member.controller;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.dto.MemberPageViewResponseDTO;
import com.example.olditemtradeplatform.member.dto.MemberRegisterRequestDTO;
import com.example.olditemtradeplatform.member.dto.MemberResponseDTO;
import com.example.olditemtradeplatform.member.dto.MemberUpdateRequestDTO;
import com.example.olditemtradeplatform.member.service.MemberService;
import com.example.olditemtradeplatform.post.dto.PostPreviewInMypageResponseDTO;
import com.example.olditemtradeplatform.post.dto.PostPreviewResponseDTO;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController implements MemberApi {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDTO> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok(MemberResponseDTO.from(member));
    }

    @GetMapping("/me/posts")
    public ResponseEntity<List<PostPreviewInMypageResponseDTO>> getMyPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok(memberService.getMyPosts(member));
    }

    @GetMapping("/me/likes")
    public ResponseEntity<List<PostPreviewResponseDTO>> getLikedPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok(memberService.getLikedPosts(member));
    }

    @GetMapping("/mypage/{nickname}")
    public ResponseEntity<MemberPageViewResponseDTO> getOtherMemberPage(@PathVariable String nickname) {
        return ResponseEntity.ok(memberService.getOtherMemberPage(nickname));
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDTO> signup(@RequestBody @Valid MemberRegisterRequestDTO request) {
        MemberResponseDTO response = memberService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<MemberResponseDTO> updateMyInfo(
            @RequestBody MemberUpdateRequestDTO requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Member member = userDetails.getMember();
        MemberResponseDTO updated = memberService.updateMember(member, requestDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteMyAccount(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();
        memberService.deleteMember(member.getId());
        return ResponseEntity.ok("회원 탈퇴 완료");
    }
}
