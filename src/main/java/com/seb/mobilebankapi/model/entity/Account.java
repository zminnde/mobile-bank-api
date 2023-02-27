package com.seb.mobilebankapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    @ManyToOne
    private Customer customer;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
}
