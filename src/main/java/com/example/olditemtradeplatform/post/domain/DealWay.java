package com.example.olditemtradeplatform.post.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DealWay {
    DIRECT,
    DELIVERY;

    @JsonCreator
    public static DealWay from(String value) {
        return DealWay.valueOf(value.toUpperCase());
    }
}