package com.example.olditemtradeplatform.authority.controller;

import com.example.olditemtradeplatform.authority.dto.SuspendRequestDTO;
import com.example.olditemtradeplatform.authority.dto.SuspendStatusResponseDTO;
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
    public ResponseEntity<List<SuspendStatusResponseDTO>> getSuspendedMembers() {
        return ResponseEntity.ok(authorityService.getSuspendedMembers());
    }

    @PostMapping("/suspend/{memberId}")
    public ResponseEntity<SuspendStatusResponseDTO> suspendMember(
            @PathVariable Long memberId,
            @RequestBody @Valid SuspendRequestDTO requestDto
    ) {
        SuspendStatusResponseDTO response = authorityService.suspendMember(memberId, requestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/unsuspend/{memberId}")
    public ResponseEntity<SuspendStatusResponseDTO> unsuspendMember(
            @PathVariable Long memberId
    ) {
        SuspendStatusResponseDTO response = authorityService.unsuspendMember(memberId);
        return ResponseEntity.ok(response);
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
