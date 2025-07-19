package com.example.olditemtradeplatform.security.service;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
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
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        checkIfAccountIsSuspended(member);

        UserDetails userDetails = new CustomUserDetails(member);
        new AccountStatusUserDetailsChecker().check(userDetails);

        return userDetails;
    }


    private void checkIfAccountIsSuspended(Member member) {
        if (member.isSuspendedNow()) {
            String until = member.getSuspendUntil().toString();
            String reason = member.getSuspendReason() == null ? "없음" : member.getSuspendReason();
            throw new DisabledException("해당 계정은 정지 상태입니다.\n정지 사유: " + reason + "\n정지 기한: " + until);
        }
    }
}