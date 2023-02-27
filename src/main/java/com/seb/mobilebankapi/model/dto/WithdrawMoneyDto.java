package com.seb.mobilebankapi.model.dto;

import java.math.BigDecimal;

public record WithdrawMoneyDto(
        BigDecimal amount
) {
}
