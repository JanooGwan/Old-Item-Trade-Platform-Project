package com.example.olditemtradeplatform.chatmessage.service;

import com.example.olditemtradeplatform.chatmessage.domain.ChatMessage;
import com.example.olditemtradeplatform.chatmessage.domain.ChatMessageId;
import com.example.olditemtradeplatform.chatmessage.dto.ChatMessageRequestDTO;
import com.example.olditemtradeplatform.chatmessage.dto.ChatMessageResponseDTO;
import com.example.olditemtradeplatform.chatmessage.dto.ReadMessageResponseDTO;
import com.example.olditemtradeplatform.chatmessage.exception.ChatMessageErrorCode;
import com.example.olditemtradeplatform.chatmessage.repository.ChatMessageRepository;
import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import com.example.olditemtradeplatform.chatroom.repository.ChatRoomRepository;
import com.example.olditemtradeplatform.global.exception.CustomException;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public ChatMessage saveChatMessage(ChatMessageRequestDTO dto) {
        ChatRoom chatRoom = chatRoomRepository.findById(dto.chatRoomId())
                .orElseThrow(() -> new CustomException(ChatMessageErrorCode.MEMBER_NOT_FOUND));

        Member sender = memberRepository.findById(dto.senderId())
                .orElseThrow(() -> new CustomException(ChatMessageErrorCode.MEMBER_NOT_FOUND));

        Long nextSentAt = chatMessageRepository.countByChatroom(chatRoom) + 1;

        ChatMessage message = ChatMessage.builder()
                .id(new ChatMessageId(chatRoom.getId(), nextSentAt))
                .sender(sender)
                .chatroom(chatRoom)
                .content(dto.content())
                .isRead(false)
                .sentAt(nextSentAt)
                .build();

        return chatMessageRepository.save(message);
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponseDTO> getMessagesByRoomId(Long chatRoomId, Long memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new CustomException(ChatMessageErrorCode.CHATROOM_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ChatMessageErrorCode.MEMBER_NOT_FOUND));

        if (!chatRoom.hasParticipant(member)) {
            throw new CustomException(ChatMessageErrorCode.UNAUTHORIZED_CHAT_ACCESS);
        }

        return chatMessageRepository.findByChatroomOrderBySentAtAsc(chatRoom).stream()
                .map(ChatMessageResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ChatMessageResponseDTO findMessage(ChatMessageId id) {
        ChatMessage message = chatMessageRepository.findById(id)
                .orElseThrow(() -> new CustomException(ChatMessageErrorCode.MESSAGE_NOT_FOUND));

        return ChatMessageResponseDTO.from(message);
    }

    @Transactional
    public void markMessagesAsRead(Long chatRoomId, Long sentAt, Long readerId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new CustomException(ChatMessageErrorCode.CHATROOM_NOT_FOUND));

        Member reader = memberRepository.findById(readerId)
                .orElseThrow(() -> new CustomException(ChatMessageErrorCode.MEMBER_NOT_FOUND));

        if (!chatRoom.hasParticipant(reader)) {
            throw new CustomException(ChatMessageErrorCode.UNAUTHORIZED_CHAT_ACCESS);
        }

        List<ChatMessage> unreadMessages = chatMessageRepository
                .findByChatroomOrderBySentAtAsc(chatRoom).stream()
                .filter(m -> !m.isRead() && m.getSentAt() <= sentAt && !m.getSender().getId().equals(readerId))
                .toList();

        unreadMessages.forEach(ChatMessage::updateReadStatus);

        messagingTemplate.convertAndSend(
                "/topic/chatroom." + chatRoomId,
                new ReadMessageResponseDTO(chatRoomId, readerId)
        );
    }

    @Transactional
    public void deleteMessage(ChatMessageId id) {
        chatMessageRepository.deleteById(id);
    }
}
