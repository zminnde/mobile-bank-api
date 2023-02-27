package com.seb.mobilebankapi.service;

import com.seb.mobilebankapi.model.TransferType;
import com.seb.mobilebankapi.model.dto.AccountDto;
import com.seb.mobilebankapi.model.dto.DepositMoneyDto;
import com.seb.mobilebankapi.model.dto.TransferMoneyDto;
import com.seb.mobilebankapi.model.dto.WithdrawMoneyDto;
import com.seb.mobilebankapi.model.entity.Account;
import com.seb.mobilebankapi.model.entity.AccountType;
import com.seb.mobilebankapi.repository.AccountRepository;
import com.seb.mobilebankapi.service.mapper.AccountMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.seb.mobilebankapi.model.entity.AccountType.SAVINGS;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountDto depositMoney(String accountNumber, DepositMoneyDto depositMoneyDto, String authenticatedUserName) {
        var accountToUpdate = accountRepository.findByAccountNumber(accountNumber);

        validateAuthenticatedOperation(accountToUpdate, authenticatedUserName);

        accountToUpdate.setBalance(accountToUpdate.getBalance().add(depositMoneyDto.amount()));
        var updatedAccount = accountRepository.save(accountToUpdate);
        return accountMapper.entityToDto(updatedAccount);
    }

    public AccountDto withdrawMoney(String accountNumber, WithdrawMoneyDto withdrawMoneyDto, String authenticatedUserName) {
        var accountToUpdate = accountRepository.findByAccountNumber(accountNumber);

        validateAuthenticatedOperation(accountToUpdate, authenticatedUserName);
        validateWithdrawalAllowed(accountToUpdate.getAccountType());
        validateSufficientFunds(accountToUpdate.getBalance(), withdrawMoneyDto.amount());

        accountToUpdate.setBalance(accountToUpdate.getBalance().subtract(withdrawMoneyDto.amount()));
        var savedAccount = accountRepository.save(accountToUpdate);
        return accountMapper.entityToDto(savedAccount);
    }

    @Transactional
    public AccountDto transferMoney(String sourceAccountNumber, TransferMoneyDto transferMoneyDto, String authenticatedUserName) {
        var sourceAccount = accountRepository.findByAccountNumber(sourceAccountNumber);
        validateAuthenticatedOperation(sourceAccount, authenticatedUserName);
        validateTransferAllowed(sourceAccount.getAccountType());
        var targetAccount = accountRepository.findByAccountNumber(transferMoneyDto.targetAccountNumber());

        var transferType = resolveTransferType(sourceAccount.getId(), targetAccount.getId());
        validateTransferAmount(transferMoneyDto.amount(), transferType);
        validateSufficientFunds(sourceAccount.getBalance(), transferMoneyDto.amount());

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transferMoneyDto.amount()));
        targetAccount.setBalance(targetAccount.getBalance().add(transferMoneyDto.amount()));
        accountRepository.saveAll(List.of(sourceAccount, targetAccount));
        return accountMapper.entityToDto(sourceAccount);
    }

    private void validateAuthenticatedOperation(Account accountToUpdate, String authenticatedUserName) {
        if (!accountToUpdate.getCustomer().getUserName().equals(authenticatedUserName)) {
            throw new IllegalArgumentException("Cannot deposit/withdraw from another user's account");
        }
    }

    private void validateWithdrawalAllowed(AccountType accountType) {
        if (accountType.equals(SAVINGS)) {
            throw new UnsupportedOperationException("Cannot withdraw from savings account");
        }
    }

    private void validateTransferAllowed(AccountType accountType) {
        if (accountType.equals(SAVINGS)) {
            throw new UnsupportedOperationException("Cannot transfer from savings account");
        }
    }

    private void validateSufficientFunds(BigDecimal balance, BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    private void validateTransferAmount(BigDecimal amount, TransferType transferType) {
        if (amount.compareTo(transferType.getMaxAmount()) > 0) {
            throw new IllegalArgumentException("%s transfer amount cannot be higher than %s".formatted(transferType, transferType.getMaxAmount()));
        }
    }

    private TransferType resolveTransferType(Long sourceCustomerId, Long targetCustomerId) {
        if (sourceCustomerId.equals(targetCustomerId)) {
            return TransferType.INTERNAL;
        }
        return TransferType.EXTERNAL;
    }
}


