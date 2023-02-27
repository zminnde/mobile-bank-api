package com.seb.mobilebankapi.service.mapper;

import com.seb.mobilebankapi.model.dto.CreateCustomerDto;
import com.seb.mobilebankapi.model.dto.CustomerDto;
import com.seb.mobilebankapi.model.entity.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerMapper {
    private final AccountMapper accountMapper;

    public CustomerDto entityToDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getUserName(),
                customer.getAccounts().stream().map(accountMapper::entityToDto).toList()
        );
    }

    public Customer createDtoToEntity(CreateCustomerDto createCustomerDto) {
        return Customer.builder()
                .userName(createCustomerDto.userName())
                .build();
    }
}
