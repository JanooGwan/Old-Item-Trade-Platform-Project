package com.example.olditemtradeplatform.chatroom.repository;

import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import com.example.olditemtradeplatform.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByMember1OrMember2(Member member1, Member member2);
    Optional<ChatRoom> findByMember1AndMember2(Member member1, Member member2);
    Optional<ChatRoom> findByMember1AndMember2OrMember2AndMember1(
            Member member1, Member member2, Member member2Alt, Member member1Alt);
}
