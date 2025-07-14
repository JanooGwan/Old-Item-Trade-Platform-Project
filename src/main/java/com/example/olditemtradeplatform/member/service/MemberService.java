package com.example.olditemtradeplatform.member.service;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.dto.MemberRegisterRequestDTO;
import com.example.olditemtradeplatform.member.dto.MemberResponseDTO;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.olditemtradeplatform.member.dto.MemberUpdateRequestDTO;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponseDTO register(MemberRegisterRequestDTO dto, String encodedPassword) {
        Member member = dto.toEntity(encodedPassword);
        memberRepository.save(member);
        return MemberResponseDTO.from(member);
    }

    @Transactional(readOnly = true)
    public MemberResponseDTO findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return MemberResponseDTO.from(member);
    }


    public Optional<Member> findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }


    @Transactional
    public MemberResponseDTO updateMember(Member member, MemberUpdateRequestDTO dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        member.updateMember(encodedPassword, dto.getEmail(), dto.getNickname());
        return MemberResponseDTO.from(member);
    }



    @Transactional
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}