package com.example.olditemtradeplatform.security.service;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (member.getSuspendUntil() != null && member.getSuspendUntil().isAfter(LocalDate.now())) {
            String until = member.getSuspendUntil().toString();
            String reason = member.getSuspendReason() == null ? "없음" : member.getSuspendReason();
            throw new DisabledException("당신은 정지 상태입니다.\n\n정지 해제일: " + until + "\n사유: " + reason);
        }

        return new CustomUserDetails(member);
    }

}
