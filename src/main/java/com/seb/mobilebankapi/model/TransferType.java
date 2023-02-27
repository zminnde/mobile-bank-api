package com.seb.mobilebankapi.model;

import java.math.BigDecimal;

public enum TransferType {
    INTERNAL("100000"), EXTERNAL("15000");

    private final BigDecimal maxAmount;

    TransferType(String maxAmount) {
        this.maxAmount = new BigDecimal(maxAmount);
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }
}