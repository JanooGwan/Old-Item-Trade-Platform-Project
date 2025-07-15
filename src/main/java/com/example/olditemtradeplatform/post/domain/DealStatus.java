package com.example.olditemtradeplatform.post.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DealStatus {
    WAITING,
    IN_PROGRESS,
    COMPLETED;

    @JsonCreator
    public static DealStatus from(String value) {
        return DealStatus.valueOf(value.toUpperCase());
    }
}
