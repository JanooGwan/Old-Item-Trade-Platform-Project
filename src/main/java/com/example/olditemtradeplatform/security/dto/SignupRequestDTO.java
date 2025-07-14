package com.example.olditemtradeplatform.security.dto;

public record SignupRequestDTO(
        String userId,
        String email,
        String password,
        String nickname
) {}
