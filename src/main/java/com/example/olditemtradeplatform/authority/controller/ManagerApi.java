package com.example.olditemtradeplatform.authority.controller;

import com.example.olditemtradeplatform.authority.dto.SuspendRequestDTO;
import com.example.olditemtradeplatform.authority.dto.SuspendedMemberResponseDTO;
import com.example.olditemtradeplatform.reportofpost.dto.ReportOfPostResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "운영자 API", description = "운영자 또는 관리자 권한으로 신고 관리 및 회원 정지/해제 기능 제공")
@RequestMapping("/api/manager")
public interface ManagerApi {

    @Operation(summary = "신고된 게시글 목록 조회", description = "신고된 게시글들을 리스트로 조회합니다.")
    @ApiResponse(responseCode = "200", description = "신고 목록 조회 성공", content = @Content(mediaType = "application/json"))
    @GetMapping("/post-reports")
    ResponseEntity<List<ReportOfPostResponseDTO>> getReportedPosts();

    @Operation(summary = "정지된 회원 목록 조회", description = "현재 정지된 회원 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "정지 회원 목록 조회 성공", content = @Content(mediaType = "application/json"))
    @GetMapping("/suspended-members")
    ResponseEntity<List<SuspendedMemberResponseDTO>> getSuspendedMembers();

    @Operation(summary = "회원 정지", description = "특정 회원을 정지합니다.")
    @ApiResponse(responseCode = "200", description = "회원 정지 성공", content = @Content(mediaType = "application/json"))
    @PostMapping("/suspend")
    ResponseEntity<String> suspendMember(
            @RequestBody(
                    description = "정지할 회원의 정보 및 정지 기한",
                    required = true,
                    content = @Content(mediaType = "application/json")
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody SuspendRequestDTO requestDto
    );

    @Operation(summary = "회원 정지 해제", description = "특정 회원의 정지를 해제합니다.")
    @ApiResponse(responseCode = "200", description = "회원 정지 해제 성공", content = @Content(mediaType = "application/json"))
    @PostMapping("/unsuspend/{memberId}")
    ResponseEntity<String> unsuspendMember(
            @Parameter(description = "정지 해제할 회원의 ID", example = "1")
            @PathVariable Long memberId
    );

    @Operation(summary = "신고 기각", description = "특정 게시글에 대한 신고를 기각 처리합니다.")
    @ApiResponse(responseCode = "204", description = "신고 기각 처리 완료")
    @DeleteMapping("/post-reports/{postId}/{reporterId}")
    ResponseEntity<Void> dismissReport(
            @Parameter(description = "신고된 게시글 ID", example = "10")
            @PathVariable Long postId,

            @Parameter(description = "신고한 회원 ID", example = "5")
            @PathVariable Long reporterId
    );
}
