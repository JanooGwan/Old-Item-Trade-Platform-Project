package com.example.olditemtradeplatform.authority.controller;

import com.example.olditemtradeplatform.authority.dto.SuspendRequestDTO;
import com.example.olditemtradeplatform.authority.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequestMapping("/api/authority")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    @PostMapping("/suspend")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<String> suspendMember(@RequestBody SuspendRequestDTO requestDto) {
        authorityService.suspendMember(requestDto);
        return ResponseEntity.ok("회원이 정지되었습니다.");
    }
    @PostMapping("/unsuspend/{memberId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<String> unsuspendMember(@PathVariable Long memberId) {
        authorityService.unsuspendMember(memberId);
        return ResponseEntity.ok("회원 정지가 해제되었습니다.");
    }
}
