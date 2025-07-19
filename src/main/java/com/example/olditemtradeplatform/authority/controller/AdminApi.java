package com.example.olditemtradeplatform.authority.controller;

import com.example.olditemtradeplatform.member.dto.MemberResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "관리자 API", description = "관리자 권한으로 회원 관리 및 권한 변경 기능 제공")
@RequestMapping("/api/admin")
public interface AdminApi {

    @Operation(
            summary = "전체 회원 조회",
            description = "관리자가 전체 회원 목록을 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "회원 목록 조회 성공", content = @Content(mediaType = "application/json"))
    @GetMapping("/members")
    ResponseEntity<List<MemberResponseDTO>> getAllMembers();

    @Operation(
            summary = "회원 권한 변경",
            description = "지정한 회원의 권한을 변경합니다. 요청 본문에 'role' 키로 새로운 권한을 지정해야 합니다."
    )
    @ApiResponse(responseCode = "200", description = "권한 변경 성공", content = @Content(mediaType = "application/json"))
    @PostMapping("/change-role/{memberId}")
    ResponseEntity<String> changeRole(
            @Parameter(description = "변경할 회원의 ID", example = "1")
            @PathVariable Long memberId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "변경할 권한 정보 (예: role: USER / MANAGER / ADMIN)",
                    required = true,
                    content = @Content(mediaType = "application/json")
            )
            @RequestBody @Valid Map<String, String> body
    );

    @Operation(
            summary = "회원 강제 탈퇴",
            description = "지정한 회원을 강제로 탈퇴 처리합니다."
    )
    @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공", content = @Content(mediaType = "application/json"))
    @DeleteMapping("/member/{memberId}")
    ResponseEntity<Map<String, String>> forceWithdraw(
            @Parameter(description = "탈퇴시킬 회원의 ID", example = "1")
            @PathVariable Long memberId
    );
}
