package com.example.olditemtradeplatform.chatroom.controller;

import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import com.example.olditemtradeplatform.chatroom.dto.ChatRoomResponseDTO;
import com.example.olditemtradeplatform.chatroom.service.ChatRoomService;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatrooms")
public class ChatRoomController implements ChatRoomApi {

    private final ChatRoomService chatRoomService;
    private final MemberRepository memberRepository;

    @GetMapping
    public ResponseEntity<List<ChatRoomResponseDTO>> getMyChatRooms(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMember().getId();
        List<ChatRoomResponseDTO> chatRooms = chatRoomService.getChatRoomsByMemberId(memberId);
        return ResponseEntity.ok(chatRooms);
    }

    @PostMapping("/enter")
    public ResponseEntity<ChatRoomResponseDTO> enterChatRoom(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody Map<String, Long> body
    ) {
        Long me = userDetails.getMember().getId();
        Long other = body.get("memberId");

        ChatRoom room = chatRoomService.getOrCreateChatRoom(me, other);
        return ResponseEntity.ok(ChatRoomResponseDTO.from(room, me));
    }

    @PostMapping("/enter-by-nickname")
    public ResponseEntity<ChatRoomResponseDTO> enterChatRoomByNickname(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody Map<String, String> body
    ) {
        String nickname = body.get("nickname");
        Member me = userDetails.getMember();

        Member target = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("상대방 닉네임을 찾을 수 없습니다."));

        ChatRoom room = chatRoomService.getOrCreateChatRoom(me.getId(), target.getId());
        return ResponseEntity.ok(ChatRoomResponseDTO.from(room, me.getId()));
    }
}
