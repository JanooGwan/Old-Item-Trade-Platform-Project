package com.example.olditemtradeplatform.security.service;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디는 존재하지 않습니다."));

        if (member.isSuspendedNow()) {
            String reason = member.getSuspendReason() == null ? "없음" : member.getSuspendReason();
            String until = member.getSuspendUntil() == null ? "미정" : member.getSuspendUntil().toString();
            throw new DisabledException("해당 계정은 정지 상태입니다.\n\n정지 사유: " + reason + "\n정지 기한: " + until);
        }

        return new CustomUserDetails(member);
    }
}
