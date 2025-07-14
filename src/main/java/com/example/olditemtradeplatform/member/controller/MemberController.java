package com.example.olditemtradeplatform.member.controller;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.dto.MemberResponseDTO;
import com.example.olditemtradeplatform.member.dto.MemberUpdateRequestDTO;
import com.example.olditemtradeplatform.member.service.MemberService;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDTO> getMyInfo(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        MemberResponseDTO dto = MemberResponseDTO.from(member);
        return ResponseEntity.ok(dto);
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
