package com.example.olditemtradeplatform.security.service;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import com.example.olditemtradeplatform.security.dto.LoginRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private static final int SESSION_TIMEOUT_SECONDS = 1800;

    @Transactional
    public void login(LoginRequestDTO request, HttpServletRequest httpRequest) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(request.email(), request.password());

            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Member member = ((CustomUserDetails) authentication.getPrincipal()).getUser();
            createSession(httpRequest, member);

        } catch (AuthenticationException e) {
            throw new RuntimeException("이메일 또는 비밀번호가 일치하지 않습니다.", e);
        }
    }

    private void createSession(HttpServletRequest request, Member member) {
        HttpSession session = request.getSession(true);
        session.setAttribute("memberId", member.getId());
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        session.setMaxInactiveInterval(SESSION_TIMEOUT_SECONDS);
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            SecurityContextHolder.clearContext();
        }
    }
}
