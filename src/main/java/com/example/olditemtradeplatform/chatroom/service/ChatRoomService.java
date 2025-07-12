package com.example.olditemtradeplatform.chatroom.service;

import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import com.example.olditemtradeplatform.chatroom.dto.ChatRoomResponseDTO;
import com.example.olditemtradeplatform.chatroom.repository.ChatRoomRepository;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ChatRoomResponseDTO createRoom(Long member1Id, Long member2Id) {
        Member member1 = memberRepository.findById(member1Id)
                .orElseThrow(() -> new IllegalArgumentException("Member1 not found"));
        Member member2 = memberRepository.findById(member2Id)
                .orElseThrow(() -> new IllegalArgumentException("Member2 not found"));

        ChatRoom room = new ChatRoom(member1, member2);
        chatRoomRepository.save(room);

        return ChatRoomResponseDTO.from(room);
    }

    @Transactional(readOnly = true)
    public ChatRoomResponseDTO findRoom(Long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found"));
        return ChatRoomResponseDTO.from(room);
    }

    @Transactional
    public void deleteRoom(Long roomId) {
        chatRoomRepository.deleteById(roomId);
    }
}
