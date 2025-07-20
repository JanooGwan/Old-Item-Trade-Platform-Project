package com.example.olditemtradeplatform.security.controller;

import com.example.olditemtradeplatform.member.dto.MemberLoginResponseDTO;
import com.example.olditemtradeplatform.member.dto.MemberRegisterRequestDTO;
import com.example.olditemtradeplatform.member.service.MemberService;
import com.example.olditemtradeplatform.security.dto.LoginRequestDTO;
import com.example.olditemtradeplatform.security.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auths")
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO request,
            HttpServletRequest httpRequest
    ) {
        MemberLoginResponseDTO responseDTO = authService.login(request.userId(), request.password(), httpRequest);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok("로그아웃 성공");
    }

}
