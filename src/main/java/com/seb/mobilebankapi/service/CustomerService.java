package com.seb.mobilebankapi.service;

import com.seb.mobilebankapi.model.dto.AccountDto;
import com.seb.mobilebankapi.repository.AccountRepository;
import com.seb.mobilebankapi.repository.CustomerRepository;
import com.seb.mobilebankapi.service.mapper.AccountMapper;
import com.seb.mobilebankapi.service.mapper.CustomerMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record CustomerService(
        CustomerRepository customerRepository,
        CustomerMapper customerMapper,
        AccountMapper accountMapper,
        AccountRepository accountRepository
) {

    public List<AccountDto> getAccountsById(Long id, String authenticatedUserName) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id: %s not found".formatted(id)))
                .getAccounts()
                .stream()
                .map(accountMapper::entityToDto)
                .toList();
    }

}
