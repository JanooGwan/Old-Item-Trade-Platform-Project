package com.example.olditemtradeplatform.security.service;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.dto.MemberLoginResponseDTO;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;


    public MemberLoginResponseDTO login(String userId, String password, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userId, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = request.getSession();
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Member member = memberRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디는 존재하지 않습니다."));

        return MemberLoginResponseDTO.of(member.getId(), member.getNickname(), member.getRole().name());
    }


    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

}
