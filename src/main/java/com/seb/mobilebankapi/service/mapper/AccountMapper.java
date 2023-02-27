package com.seb.mobilebankapi.service.mapper;

import com.seb.mobilebankapi.model.dto.AccountDto;
import com.seb.mobilebankapi.model.entity.Account;
import org.springframework.stereotype.Component;

@Component
public record AccountMapper() {
    public AccountDto entityToDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getAccountType()
        );
    }
}
