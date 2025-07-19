package com.example.olditemtradeplatform.chatroom.controller;

import com.example.olditemtradeplatform.chatroom.dto.ChatRoomResponseDTO;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "채팅방 API", description = "내 채팅방 조회 및 채팅방 입장 관련 API")
@RequestMapping("/api/chatrooms")
public interface ChatRoomApi {

    @Operation(summary = "내 채팅방 목록 조회", description = "현재 로그인한 사용자의 채팅방 리스트를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json"))
    @GetMapping
    List<ChatRoomResponseDTO> getMyChatRooms(
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "상대 ID로 채팅방 입장", description = "상대방의 회원 ID를 통해 채팅방에 입장하거나 새로 생성합니다.")
    @ApiResponse(responseCode = "200", description = "입장 성공", content = @Content(mediaType = "application/json"))
    @PostMapping("/enter")
    ChatRoomResponseDTO enterChatRoom(
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails,

            @RequestBody(
                    description = "{ \"memberId\": 상대방 ID } 형식의 JSON 요청",
                    required = true,
                    content = @Content(mediaType = "application/json")
            )
            @org.springframework.web.bind.annotation.RequestBody Map<String, Long> body
    );

    @Operation(summary = "상대 닉네임으로 채팅방 입장", description = "상대방 닉네임을 통해 채팅방에 입장하거나 새로 생성합니다.")
    @ApiResponse(responseCode = "200", description = "입장 성공", content = @Content(mediaType = "application/json"))
    @PostMapping("/enter-by-nickname")
    ChatRoomResponseDTO enterChatRoomByNickname(
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails,

            @RequestBody(
                    description = "{ \"nickname\": \"상대 닉네임\" } 형식의 JSON 요청",
                    required = true,
                    content = @Content(mediaType = "application/json")
            )
            @org.springframework.web.bind.annotation.RequestBody Map<String, String> body
    );
}
