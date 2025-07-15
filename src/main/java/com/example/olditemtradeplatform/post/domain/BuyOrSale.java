package com.example.olditemtradeplatform.post.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BuyOrSale {
    BUY, SALE;

    @JsonCreator
    public static DealStatus from(String value) {
        return DealStatus.valueOf(value.toUpperCase());
    }
}
