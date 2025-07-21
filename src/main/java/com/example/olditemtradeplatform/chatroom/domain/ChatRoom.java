package com.example.olditemtradeplatform.chatroom.domain;

import com.example.olditemtradeplatform.chatmessage.domain.ChatMessage;
import com.example.olditemtradeplatform.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member1_id", nullable = false)
    Member member1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member2_id", nullable = false)
    Member member2;

    @Builder.Default
    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ChatMessage> chatmessages = new ArrayList<>();


    public ChatRoom(Member member1, Member member2) {
        this.member1 = member1;
        this.member2 = member2;
    }

    public boolean hasParticipant(Member member) {
        return member1.getId().equals(member.getId()) || member2.getId().equals(member.getId());
    }
}
