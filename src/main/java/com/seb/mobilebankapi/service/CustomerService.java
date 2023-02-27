package com.seb.mobilebankapi.service;

import com.seb.mobilebankapi.model.dto.AccountDto;
import com.seb.mobilebankapi.repository.CustomerRepository;
import com.seb.mobilebankapi.service.mapper.AccountMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.seb.mobilebankapi.util.DataAccessUtils.getEntity;
import static com.seb.mobilebankapi.util.OperationValidationUtils.validateAuthorizedOperation;

@Service
public final class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountMapper accountMapper;

    public CustomerService(CustomerRepository customerRepository, AccountMapper accountMapper) {
        this.customerRepository = customerRepository;
        this.accountMapper = accountMapper;
    }

    public List<AccountDto> getAccountsById(Long id, String authenticatedUserName) {
        var customer = getEntity(customerRepository::findById, id);
        validateAuthorizedOperation(customer.getUserName(), authenticatedUserName);
        return customer
                .getAccounts()
                .stream()
                .map(accountMapper::entityToDto)
                .toList();
    }
}
