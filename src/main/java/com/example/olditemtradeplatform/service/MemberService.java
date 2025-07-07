package com.example.olditemtradeplatform.service;

import com.example.olditemtradeplatform.entity.Member;
import com.example.olditemtradeplatform.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member register(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}