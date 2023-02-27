package com.seb.mobilebankapi.controller;

import com.seb.mobilebankapi.model.dto.AccountDto;
import com.seb.mobilebankapi.model.dto.DepositMoneyDto;
import com.seb.mobilebankapi.model.dto.TransferMoneyDto;
import com.seb.mobilebankapi.model.dto.WithdrawMoneyDto;
import com.seb.mobilebankapi.service.AccountService;
import com.seb.mobilebankapi.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public record AccountController(
        AccountService accountService,
        CustomerService customerService
) {

    @GetMapping()
    public List<AccountDto> getAccountsByCustomerId(@RequestParam Long customerId, Principal principal) {
        return customerService.getAccountsById(customerId, principal.getName());
    }

    @PatchMapping("/{accountNumber}/deposit")
    public AccountDto depositMoney(
            @PathVariable String accountNumber,
            @RequestBody @Valid DepositMoneyDto depositMoneyDto,
            Principal principal
    ) {
        return accountService.depositMoney(accountNumber, depositMoneyDto, principal.getName());
    }

    @PatchMapping("/{accountNumber}/withdraw")
    public AccountDto withdrawMoney(
            @PathVariable String accountNumber,
            @RequestBody @Valid WithdrawMoneyDto withdrawMoneyDto,
            Principal principal
    ) {
        return accountService.withdrawMoney(accountNumber, withdrawMoneyDto, principal.getName());
    }

    @PatchMapping("/{accountNumber}/transfer")
    public AccountDto transferMoney(
            @PathVariable String accountNumber,
            @RequestBody @Valid TransferMoneyDto transferMoneyDto,
            Principal principal
    ) {
        return accountService.transferMoney(accountNumber, transferMoneyDto, principal.getName());
    }
}

