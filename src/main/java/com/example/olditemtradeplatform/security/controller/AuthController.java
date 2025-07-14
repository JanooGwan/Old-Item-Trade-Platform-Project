package com.example.olditemtradeplatform.security.controller;

import com.example.olditemtradeplatform.member.dto.MemberRegisterRequestDTO;
import com.example.olditemtradeplatform.member.service.MemberService;
import com.example.olditemtradeplatform.security.dto.LoginRequestDTO;
import com.example.olditemtradeplatform.security.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auths")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO request, HttpServletRequest httpRequest) {
        authService.login(request, httpRequest);
        return ResponseEntity.ok("로그인 성공");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok("로그아웃 성공");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody MemberRegisterRequestDTO request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        memberService.register(request, encodedPassword);
        return ResponseEntity.ok("회원가입 성공");
    }
}
