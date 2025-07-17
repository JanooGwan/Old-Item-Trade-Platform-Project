package com.example.olditemtradeplatform.chatmessage.controller;

import com.example.olditemtradeplatform.chatmessage.dto.ChatMessageResponseDTO;
import com.example.olditemtradeplatform.chatmessage.service.ChatMessageService;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatrooms/{roomId}/messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping
    public List<ChatMessageResponseDTO> getMessagesByChatRoom(
            @PathVariable Long roomId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long memberId = userDetails.getMember().getId();
        return chatMessageService.getMessagesByRoomId(roomId, memberId);
    }

    @PostMapping("/{sentAt}/read")
    public void markAsRead(
            @PathVariable Long roomId,
            @PathVariable Long sentAt,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long memberId = userDetails.getMember().getId();
        chatMessageService.markMessagesAsRead(roomId, sentAt, memberId);
    }
}

