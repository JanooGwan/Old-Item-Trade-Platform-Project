package com.example.olditemtradeplatform.chat.controller;

import com.example.olditemtradeplatform.chatmessage.dto.ChatMessageRequestDTO;
import com.example.olditemtradeplatform.chatmessage.dto.ChatMessageResponseDTO;
import com.example.olditemtradeplatform.chatmessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageRequestDTO requestDTO) {
        ChatMessageResponseDTO responseDTO = ChatMessageResponseDTO.from(
                chatMessageService.saveChatMessage(requestDTO)
        );

        String destination = "/topic/chatroom." + responseDTO.getChatRoomId();
        messagingTemplate.convertAndSend(destination, responseDTO);
    }
}
