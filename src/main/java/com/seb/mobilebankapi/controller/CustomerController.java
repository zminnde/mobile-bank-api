package com.seb.mobilebankapi.controller;

import com.seb.mobilebankapi.model.dto.AccountDto;
import com.seb.mobilebankapi.repository.AccountRepository;
import com.seb.mobilebankapi.repository.CustomerRepository;
import com.seb.mobilebankapi.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public record CustomerController(
        CustomerService customerService,
        CustomerRepository customerRepository,
        AccountRepository accountRepository
) {

    @GetMapping("/accounts/{customerId}")
    public List<AccountDto> getAccountsByCustomerId(@PathVariable Long customerId, Principal principal) {
        return customerService.getAccountsById(customerId, principal.getName());
    }

}
