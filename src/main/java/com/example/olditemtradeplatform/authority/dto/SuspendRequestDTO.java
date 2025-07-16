package com.example.olditemtradeplatform.authority.dto;

import com.example.olditemtradeplatform.member.domain.Member;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SuspendRequestDTO {

    private Long postId;
    private Long reporterId;
    private LocalDate suspendUntil;
}