package com.example.olditemtradeplatform.chatmessage.domain;

import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import com.example.olditemtradeplatform.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatMessage {

    @EmbeddedId
    ChatMessageId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("senderId")
    @JoinColumn(name = "sender_id", nullable = false)
    Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    ChatRoom chatroom;

    @NotBlank
    @Column(nullable = false, length = 1000)
    String content;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    LocalDateTime sentDate;

    @Column(nullable = false, updatable = false)
    Long sentAt;

    @Column(nullable = false)
    boolean isRead;
}
