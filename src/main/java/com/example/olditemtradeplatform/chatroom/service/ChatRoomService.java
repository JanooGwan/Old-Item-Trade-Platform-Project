package com.example.olditemtradeplatform.chatroom.service;

import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import com.example.olditemtradeplatform.chatroom.dto.ChatRoomResponseDTO;
import com.example.olditemtradeplatform.chatroom.exception.ChatRoomErrorCode;
import com.example.olditemtradeplatform.chatroom.repository.ChatRoomRepository;
import com.example.olditemtradeplatform.global.exception.CustomException;
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
                .orElseThrow(() -> new CustomException(ChatRoomErrorCode.MEMBER_NOT_FOUND));

        List<ChatRoom> rooms = chatRoomRepository.findByMember1OrMember2(member, member);
        return rooms.stream()
                .map(room -> ChatRoomResponseDTO.from(room, memberId))
                .toList();
    }

    @Transactional
    public ChatRoom getOrCreateChatRoom(Long memberId1, Long memberId2) {
        if (memberId1.equals(memberId2)) {
            throw new CustomException(ChatRoomErrorCode.CANNOT_CHAT_WITH_SELF);
        }

        Member member1 = memberRepository.findById(memberId1)
                .orElseThrow(() -> new CustomException(ChatRoomErrorCode.SENDER_NOT_FOUND));

        Member member2 = memberRepository.findById(memberId2)
                .orElseThrow(() -> new CustomException(ChatRoomErrorCode.RECEIVER_NOT_FOUND));

        Optional<ChatRoom> existing = chatRoomRepository
                .findByMember1AndMember2OrMember2AndMember1(member1, member2, member1, member2);

        return existing.orElseGet(() -> chatRoomRepository.save(new ChatRoom(member1, member2)));
    }

    @Transactional
    public ChatRoom getOrCreateChatRoomByNickname(Long myId, String nickname) {
        Member me = memberRepository.findById(myId)
                .orElseThrow(() -> new CustomException(ChatRoomErrorCode.SENDER_NOT_FOUND));

        Member target = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ChatRoomErrorCode.RECEIVER_NOT_FOUND));

        if (me.getId().equals(target.getId())) {
            throw new CustomException(ChatRoomErrorCode.CANNOT_CHAT_WITH_SELF);
        }

        return chatRoomRepository
                .findByMember1AndMember2OrMember2AndMember1(me, target, me, target)
                .orElseGet(() -> chatRoomRepository.save(new ChatRoom(me, target)));
    }

}
