package com.seb.mobilebankapi.model.dto;

import java.util.List;

public record CustomerDto(
        Long id,
        String userName,
        List<AccountDto> accounts
) {
}
