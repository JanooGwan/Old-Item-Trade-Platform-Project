package com.example.olditemtradeplatform.chatroom.controller;

import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import com.example.olditemtradeplatform.chatroom.dto.ChatRoomRequestDTO;
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

    @GetMapping
    public ResponseEntity<List<ChatRoomResponseDTO>> getMyChatRooms(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMember().getId();
        List<ChatRoomResponseDTO> chatRooms = chatRoomService.getChatRoomsByMemberId(memberId);
        return ResponseEntity.ok(chatRooms);
    }

    @PostMapping("/enter")
    public ResponseEntity<ChatRoomResponseDTO> enterChatRoom(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ChatRoomRequestDTO request
    ) {
        Long me = userDetails.getMember().getId();
        Long other = request.memberId();

        ChatRoom room = chatRoomService.getOrCreateChatRoom(me, other);
        return ResponseEntity.ok(ChatRoomResponseDTO.from(room, me));
    }


    @PostMapping("/enter-by-nickname")
    public ResponseEntity<ChatRoomResponseDTO> enterChatRoomByNickname(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ChatRoomRequestDTO request
    ) {
        Long myId = userDetails.getMember().getId();
        ChatRoom room = chatRoomService.getOrCreateChatRoomByNickname(myId, request.nickname());
        return ResponseEntity.ok(ChatRoomResponseDTO.from(room, myId));
    }

}
