package com.seb.mobilebankapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Account extends PrimaryEntity {

    private String accountNumber;
    private BigDecimal balance;
    @ManyToOne
    private Customer customer;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
}
