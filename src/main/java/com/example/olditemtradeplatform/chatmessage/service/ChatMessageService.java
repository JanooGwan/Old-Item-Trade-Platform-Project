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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ChatMessage saveChatMessage(ChatMessageRequestDTO dto) {
        ChatRoom chatRoom = chatRoomRepository.findById(dto.getChatRoomId())
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found"));
        Member sender = memberRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        Long nextSentAt = chatMessageRepository.countByChatroom(chatRoom) + 1;

        ChatMessage message = ChatMessage.builder()
                .id(new ChatMessageId(chatRoom.getId(), nextSentAt))
                .sender(sender)
                .chatroom(chatRoom)
                .content(dto.getContent())
                .isRead(false)
                .sentAt(nextSentAt)
                .build();

        return chatMessageRepository.save(message);
    }


    @Transactional(readOnly = true)
    public List<ChatMessageResponseDTO> getMessagesByRoomId(Long chatRoomId, Long memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        if (!chatRoom.hasParticipant(member)) {
            throw new IllegalArgumentException("해당 채팅방에 접근할 수 없습니다.");
        }

        return chatMessageRepository.findByChatroomOrderBySentAtAsc(chatRoom).stream()
                .map(ChatMessageResponseDTO::from)
                .toList();
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
