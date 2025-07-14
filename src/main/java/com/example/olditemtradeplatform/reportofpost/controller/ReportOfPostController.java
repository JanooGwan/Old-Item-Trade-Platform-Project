package com.example.olditemtradeplatform.reportofpost.controller;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.reportofpost.dto.ReportOfPostRequestDTO;
import com.example.olditemtradeplatform.reportofpost.dto.ReportOfPostResponseDTO;
import com.example.olditemtradeplatform.reportofpost.service.ReportOfPostService;
import com.example.olditemtradeplatform.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post-reports")
@RequiredArgsConstructor
public class ReportOfPostController {

    private final ReportOfPostService reportService;

    @PostMapping
    public ResponseEntity<ReportOfPostResponseDTO> reportPost(
            @RequestBody @Valid ReportOfPostRequestDTO dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Member reporter = userDetails.getMember();
        ReportOfPostResponseDTO result = reportService.reportPost(dto, reporter);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<ReportOfPostResponseDTO>> getReports() {
        List<ReportOfPostResponseDTO> reports = reportService.getReports();
        return ResponseEntity.ok(reports);
    }
}
