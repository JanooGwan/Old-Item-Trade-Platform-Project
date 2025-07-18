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
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDTO> getMyInfo(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        MemberResponseDTO dto = MemberResponseDTO.from(member);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/me/posts")
    public ResponseEntity<List<PostPreviewInMypageResponseDTO>> getMyPosts(Authentication authentication) {
        Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();
        return ResponseEntity.ok(memberService.getMyPosts(member));
    }

    @GetMapping("/me/likes")
    public ResponseEntity<List<PostPreviewResponseDTO>> getLikedPosts(Authentication authentication) {
        Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();
        return ResponseEntity.ok(memberService.getLikedPosts(member));
    }

    @GetMapping("/mypage/{nickname}")
    public ResponseEntity<MemberPageViewResponseDTO> getOtherMemberPage(@PathVariable String nickname) {
        return ResponseEntity.ok(memberService.getOtherMemberPage(nickname));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid MemberRegisterRequestDTO request) {
        memberService.signup(request);
        return ResponseEntity.ok("회원가입 성공");
    }


    @PutMapping("/me")
    public ResponseEntity<MemberResponseDTO> updateMyInfo(
            @RequestBody MemberUpdateRequestDTO requestDto,
            Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        MemberResponseDTO updated = memberService.updateMember(member, requestDto);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteMyAccount(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        memberService.deleteMember(member.getId());
        return ResponseEntity.ok("회원 탈퇴 완료");
    }
}
