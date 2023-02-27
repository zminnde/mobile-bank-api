package com.seb.mobilebankapi.model.dto;

import com.seb.mobilebankapi.model.entity.AccountType;

import java.math.BigDecimal;

public record AccountDto(
        Long id,
        String accountNumber,
        BigDecimal balance,
        AccountType accountType
) {

}
