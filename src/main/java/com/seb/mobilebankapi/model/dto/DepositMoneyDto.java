package com.seb.mobilebankapi.model.dto;

import java.math.BigDecimal;

public record DepositMoneyDto(
        BigDecimal amount
) {

}