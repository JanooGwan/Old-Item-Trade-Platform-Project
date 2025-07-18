package com.example.olditemtradeplatform.chatroom.controller;

import com.example.olditemtradeplatform.chatmessage.dto.ChatMessageRequestDTO;
import com.example.olditemtradeplatform.chatmessage.dto.ChatMessageResponseDTO;
import com.example.olditemtradeplatform.chatmessage.dto.ReadMessageResponseDTO;
import com.example.olditemtradeplatform.chatmessage.service.ChatMessageService;
import jakarta.validation.Valid;
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
    public void sendMessage(@Valid ChatMessageRequestDTO requestDTO) {
        ChatMessageResponseDTO responseDTO = ChatMessageResponseDTO.from(
                chatMessageService.saveChatMessage(requestDTO)
        );

        String destination = "/topic/chatroom." + responseDTO.getChatRoomId();
        messagingTemplate.convertAndSend(destination, responseDTO);
    }

    @MessageMapping("/chat.read")
    public void notifyReadStatus(@Valid ReadMessageResponseDTO dto) {
        messagingTemplate.convertAndSend("/topic/chatroom." + dto.getChatRoomId(), "read-update");
    }

}
