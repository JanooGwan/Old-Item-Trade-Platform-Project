package com.example.olditemtradeplatform.authority.controller;

import com.example.olditemtradeplatform.authority.service.AuthorityService;
import com.example.olditemtradeplatform.member.dto.MemberResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final AuthorityService authorityService;

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponseDTO>> getAllMembers() {
        return ResponseEntity.ok(authorityService.getAllMembers());
    }

    @PostMapping("/change-role/{memberId}")
    public ResponseEntity<String> changeRole(
            @PathVariable Long memberId,
            @RequestBody @Valid Map<String, String> body
    ) {
        String role = body.get("role");
        authorityService.changeRole(memberId, role);
        return ResponseEntity.ok("권한이 변경되었습니다.");
    }

    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<Map<String, String>> forceWithdraw(@PathVariable Long memberId) {
        authorityService.forceWithdraw(memberId);
        return ResponseEntity.ok(Map.of("message", "회원이 강제로 탈퇴되었습니다."));
    }

}
