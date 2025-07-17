package com.example.olditemtradeplatform.chatmessage.repository;

import com.example.olditemtradeplatform.chatmessage.domain.ChatMessage;
import com.example.olditemtradeplatform.chatmessage.domain.ChatMessageId;
import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, ChatMessageId> {
    List<ChatMessage> findByChatroomOrderBySentAtAsc(ChatRoom chatRoom);
    Long countByChatroom(ChatRoom chatRoom);
}
