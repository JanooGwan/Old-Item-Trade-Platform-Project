package com.example.olditemtradeplatform.chatmessage.controller;

import com.example.olditemtradeplatform.chatmessage.dto.ChatMessageResponseDTO;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "채팅 메시지 API", description = "채팅방 내 메시지 조회 및 읽음 처리 API")
@RequestMapping("/api/chatrooms/{roomId}/messages")
public interface ChatMessageApi {

    @Operation(
            summary = "채팅방 메시지 조회",
            description = "지정한 채팅방 ID의 메시지를 로그인한 사용자 기준으로 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "메시지 조회 성공", content = @Content(mediaType = "application/json"))
    @GetMapping
    List<ChatMessageResponseDTO> getMessagesByChatRoom(
            @Parameter(description = "채팅방 ID", example = "1")
            @PathVariable Long roomId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(
            summary = "메시지 읽음 처리",
            description = "지정한 sentAt 값까지의 메시지를 읽음 처리합니다. 로그인한 사용자 기준입니다."
    )
    @ApiResponse(responseCode = "200", description = "읽음 처리 성공")
    @PostMapping("/{sentAt}/read")
    void markAsRead(
            @Parameter(description = "채팅방 ID", example = "1")
            @PathVariable Long roomId,

            @Parameter(description = "읽음 처리 기준 메시지 sentAt", example = "23")
            @PathVariable Long sentAt,

            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    );
}
