package com.example.olditemtradeplatform.authority.controller;

import com.example.olditemtradeplatform.authority.dto.SuspendRequestDTO;
import com.example.olditemtradeplatform.authority.dto.SuspendedMemberResponseDTO;
import com.example.olditemtradeplatform.authority.service.AuthorityService;
import com.example.olditemtradeplatform.reportofpost.dto.ReportOfPostResponseDTO;
import com.example.olditemtradeplatform.reportofpost.service.ReportOfPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
public class ManagerController implements ManagerApi {

    private final AuthorityService authorityService;
    private final ReportOfPostService reportService;


    @GetMapping("/post-reports")
    public ResponseEntity<List<ReportOfPostResponseDTO>> getReportedPosts() {
        return ResponseEntity.ok(reportService.getReports());
    }

    @GetMapping("/suspended-members")
    public ResponseEntity<List<SuspendedMemberResponseDTO>> getSuspendedMembers() {
        return ResponseEntity.ok(authorityService.getSuspendedMembers());
    }

    @PostMapping("/suspend")
    public ResponseEntity<String> suspendMember(@RequestBody @Valid SuspendRequestDTO requestDto) {
        authorityService.suspendMember(requestDto);
        return ResponseEntity.ok("회원이 정지되었습니다.");
    }

    @PostMapping("/unsuspend/{memberId}")
    public ResponseEntity<String> unsuspendMember(@PathVariable Long memberId) {
        authorityService.unsuspendMember(memberId);
        return ResponseEntity.ok("회원 정지가 해제되었습니다.");
    }

    @DeleteMapping("/post-reports/{postId}/{reporterId}")
    public ResponseEntity<Void> dismissReport(
            @PathVariable Long postId,
            @PathVariable Long reporterId
    ) {
        reportService.deleteReport(postId, reporterId);
        return ResponseEntity.noContent().build();
    }
}
