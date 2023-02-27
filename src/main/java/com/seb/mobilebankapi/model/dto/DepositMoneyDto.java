package com.seb.mobilebankapi.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;

public record DepositMoneyDto(
        @DecimalMin(value = "0.00", inclusive = false)
        @Digits(integer = 100, fraction = 2)
        BigDecimal amount
) {

}