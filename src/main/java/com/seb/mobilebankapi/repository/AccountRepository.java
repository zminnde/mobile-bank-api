package com.seb.mobilebankapi.repository;

import com.seb.mobilebankapi.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

//    @Query("""
//            select ba.id, ba.accountNumber, ba.balance from BankAccount ba
//            join Customer cus on ba.customer = Customer
//            where cus.userName = 'Petras'
//            """)
    List<Account> findAccountsByCustomerUserName(String userName);

    Account findAccountByAccountNumber(String accountNumber);
}
