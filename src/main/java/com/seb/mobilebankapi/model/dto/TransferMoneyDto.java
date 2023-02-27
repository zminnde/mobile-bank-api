package com.seb.mobilebankapi.model.dto;

import java.math.BigDecimal;

public record TransferMoneyDto(
        String targetAccountNumber,
        BigDecimal amount
) {
}
