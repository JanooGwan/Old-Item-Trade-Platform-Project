package com.example.olditemtradeplatform.chatroom.service;

import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import com.example.olditemtradeplatform.chatroom.dto.ChatRoomResponseDTO;
import com.example.olditemtradeplatform.chatroom.repository.ChatRoomRepository;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<ChatRoomResponseDTO> getChatRoomsByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        List<ChatRoom> rooms = chatRoomRepository.findByMember1OrMember2(member, member);
        return rooms.stream()
                .map(room -> ChatRoomResponseDTO.from(room, memberId))
                .collect(Collectors.toList());
    }

    @Transactional
    public ChatRoom getOrCreateChatRoom(Long memberId1, Long memberId2) {
        if (memberId1.equals(memberId2)) {
            throw new IllegalArgumentException("자기 자신과는 채팅할 수 없습니다.");
        }

        Member member1 = memberRepository.findById(memberId1)
                .orElseThrow(() -> new IllegalArgumentException("보내는 사용자를 찾을 수 없습니다."));
        Member member2 = memberRepository.findById(memberId2)
                .orElseThrow(() -> new IllegalArgumentException("받는 사용자를 찾을 수 없습니다."));

        Optional<ChatRoom> existing = chatRoomRepository
                .findByMember1AndMember2OrMember2AndMember1(member1, member2, member1, member2);

        return existing.orElseGet(() -> chatRoomRepository.save(new ChatRoom(member1, member2)));
    }
}
