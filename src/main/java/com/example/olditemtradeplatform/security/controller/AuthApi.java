package com.example.olditemtradeplatform.security.controller;

import com.example.olditemtradeplatform.member.dto.MemberLoginResponseDTO;
import com.example.olditemtradeplatform.security.dto.LoginRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증 API", description = "로그인 및 로그아웃 처리 API")
@RequestMapping("/auths")
public interface AuthApi {

    @Operation(summary = "로그인", description = "아이디와 비밀번호로 로그인합니다. 성공 시 세션이 생성되고 유저 정보를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberLoginResponseDTO.class)))
    @PostMapping("/login")
    ResponseEntity<MemberLoginResponseDTO> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "로그인 요청 DTO",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequestDTO.class))
            )
            @Valid @RequestBody LoginRequestDTO request,

            @Parameter(hidden = true)
            HttpServletRequest httpRequest
    );

    @Operation(summary = "로그아웃", description = "현재 로그인된 세션을 로그아웃 처리합니다.")
    @ApiResponse(responseCode = "200", description = "로그아웃 성공", content = @Content(mediaType = "application/json"))
    @PostMapping("/logout")
    ResponseEntity<String> logout(
            @Parameter(hidden = true)
            HttpServletRequest request
    );
}
