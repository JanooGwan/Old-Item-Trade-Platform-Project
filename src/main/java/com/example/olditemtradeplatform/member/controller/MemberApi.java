package com.example.olditemtradeplatform.member.controller;

import com.example.olditemtradeplatform.member.dto.*;
import com.example.olditemtradeplatform.post.dto.PostPreviewInMypageResponseDTO;
import com.example.olditemtradeplatform.post.dto.PostPreviewResponseDTO;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "회원 API", description = "회원 정보 조회, 수정, 탈퇴 및 마이페이지 관련 API")
@RequestMapping("/api/members")
public interface MemberApi {

    @Operation(summary = "내 정보 조회", description = "현재 로그인한 회원의 정보를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json"))
    @GetMapping("/me")
    ResponseEntity<MemberResponseDTO> getMyInfo(
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "내 게시글 목록 조회", description = "내가 작성한 게시글 목록을 반환합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json"))
    @GetMapping("/me/posts")
    ResponseEntity<List<PostPreviewInMypageResponseDTO>> getMyPosts(
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "내가 좋아요한 게시글 조회", description = "좋아요한 게시글 리스트를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json"))
    @GetMapping("/me/likes")
    ResponseEntity<List<PostPreviewResponseDTO>> getLikedPosts(
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "다른 회원 마이페이지 조회", description = "닉네임으로 다른 회원의 마이페이지 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json"))
    @GetMapping("/mypage/{nickname}")
    ResponseEntity<MemberPageViewResponseDTO> getOtherMemberPage(
            @Parameter(description = "조회할 대상 회원의 닉네임", example = "john_doe")
            @PathVariable String nickname
    );

    @Operation(summary = "회원가입", description = "신규 회원을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(mediaType = "application/json"))
    @PostMapping("/signup")
    ResponseEntity<MemberResponseDTO> signup(
            @RequestBody(
                    description = "회원가입 요청 DTO",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MemberRegisterRequestDTO.class)
                    )
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody MemberRegisterRequestDTO request
    );

    @Operation(summary = "내 정보 수정", description = "현재 로그인한 회원의 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(mediaType = "application/json"))
    @PutMapping("/me")
    ResponseEntity<MemberResponseDTO> updateMyInfo(
            @RequestBody(
                    description = "수정할 회원 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MemberUpdateRequestDTO.class)
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody MemberUpdateRequestDTO requestDto,

            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "회원 탈퇴", description = "현재 로그인한 회원을 탈퇴 처리합니다.")
    @ApiResponse(responseCode = "200", description = "탈퇴 성공", content = @Content(mediaType = "application/json"))
    @DeleteMapping("/me")
    ResponseEntity<String> deleteMyAccount(
            @Parameter(hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    );
}
