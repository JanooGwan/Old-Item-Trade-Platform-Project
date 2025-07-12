package com.example.olditemtradeplatform.chatmessage.service;

import com.example.olditemtradeplatform.chatmessage.domain.ChatMessage;
import com.example.olditemtradeplatform.chatmessage.domain.ChatMessageId;
import com.example.olditemtradeplatform.chatmessage.dto.ChatMessageRequestDTO;
import com.example.olditemtradeplatform.chatmessage.dto.ChatMessageResponseDTO;
import com.example.olditemtradeplatform.chatmessage.repository.ChatMessageRepository;
import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import com.example.olditemtradeplatform.chatroom.repository.ChatRoomRepository;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ChatMessageResponseDTO createMessage(Long chatRoomId, Long senderId, ChatMessageRequestDTO dto, Long sentAt) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found"));
        Member sender = memberRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        ChatMessage message = dto.toEntity(sender, chatRoom, sentAt);
        chatMessageRepository.save(message);

        return ChatMessageResponseDTO.from(message);
    }

    @Transactional(readOnly = true)
    public ChatMessageResponseDTO findMessage(ChatMessageId id) {
        ChatMessage message = chatMessageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));
        return ChatMessageResponseDTO.from(message);
    }

    @Transactional
    public void deleteMessage(ChatMessageId id) {
        chatMessageRepository.deleteById(id);
    }
}
