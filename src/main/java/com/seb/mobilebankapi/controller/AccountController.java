package com.seb.mobilebankapi.controller;

import com.seb.mobilebankapi.model.dto.AccountDto;
import com.seb.mobilebankapi.model.dto.DepositMoneyDto;
import com.seb.mobilebankapi.model.dto.TransferMoneyDto;
import com.seb.mobilebankapi.model.dto.WithdrawMoneyDto;
import com.seb.mobilebankapi.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/accounts/{accountNumber}")
public record AccountController(
        AccountService accountService
) {
    @PatchMapping("/deposit")
    public AccountDto depositMoney(
            @PathVariable String accountNumber,
            @RequestBody DepositMoneyDto depositMoneyDto,
            Principal principal
    ) {
        return accountService.depositMoney(accountNumber, depositMoneyDto, principal.getName());
    }

    @PatchMapping("/withdraw")
    public AccountDto withdrawMoney(
            @PathVariable String accountNumber,
            @RequestBody WithdrawMoneyDto withdrawMoneyDto,
            Principal principal
    ) {
        return accountService.withdrawMoney(accountNumber, withdrawMoneyDto, principal.getName());
    }

    @PatchMapping("/transfer")
    public AccountDto transferMoney(
            @PathVariable String accountNumber,
            @RequestBody TransferMoneyDto transferMoneyDto,
            Principal principal
    ) {
        return accountService.transferMoney(accountNumber, transferMoneyDto, principal.getName());
    }
}

