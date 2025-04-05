package com.example.olditemtradeplatform.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "member1_id", nullable = false)
    Member member1;

    @ManyToOne
    @JoinColumn(name = "member2_id", nullable = false)
    Member member2;

    @OneToMany(mappedBy = "chatroom")
    List<ChatMessage> chatmessages = new ArrayList<>();

    public boolean hasParticipant(Member member) {
        return member1.equals(member) || member2.equals(member);
    }
}
