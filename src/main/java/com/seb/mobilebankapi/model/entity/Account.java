package com.seb.mobilebankapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    @ManyToOne
    private Customer customer;
//    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
}
