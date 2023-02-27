package com.seb.mobilebankapi.service;

import com.seb.mobilebankapi.model.dto.AccountDto;
import com.seb.mobilebankapi.model.dto.DepositMoneyDto;
import com.seb.mobilebankapi.model.dto.TransferMoneyDto;
import com.seb.mobilebankapi.model.dto.WithdrawMoneyDto;
import com.seb.mobilebankapi.repository.AccountRepository;
import com.seb.mobilebankapi.service.mapper.AccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.seb.mobilebankapi.util.DataAccessUtils.getEntity;
import static com.seb.mobilebankapi.util.OperationValidationUtils.*;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public AccountDto depositMoney(String accountNumber, DepositMoneyDto depositMoneyDto, String authenticatedUserName) {
        var accountToUpdate = getEntity(accountRepository::findByAccountNumber, accountNumber);
        validateAuthorizedOperation(accountToUpdate.getCustomer().getUserName(), authenticatedUserName);

        accountToUpdate.setBalance(accountToUpdate.getBalance().add(depositMoneyDto.amount()));
        var updatedAccount = accountRepository.save(accountToUpdate);
        return accountMapper.entityToDto(updatedAccount);
    }

    public AccountDto withdrawMoney(String accountNumber, WithdrawMoneyDto withdrawMoneyDto, String authenticatedUserName) {
        var accountToUpdate = getEntity(accountRepository::findByAccountNumber, accountNumber);
        validateWithdrawalAllowed(accountToUpdate, authenticatedUserName, withdrawMoneyDto.amount());

        accountToUpdate.setBalance(accountToUpdate.getBalance().subtract(withdrawMoneyDto.amount()));
        var savedAccount = accountRepository.save(accountToUpdate);
        return accountMapper.entityToDto(savedAccount);
    }

    @Transactional
    public AccountDto transferMoney(String sourceAccountNumber, TransferMoneyDto transferMoneyDto, String authenticatedUserName) {
        var sourceAccount = getEntity(accountRepository::findByAccountNumber, sourceAccountNumber);
        validateWithdrawalAllowed(sourceAccount, authenticatedUserName, transferMoneyDto.amount());

        var targetAccount = getEntity(accountRepository::findByAccountNumber, transferMoneyDto.targetAccountNumber());
        validateTransferAllowed(sourceAccount, targetAccount, transferMoneyDto.amount());

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transferMoneyDto.amount()));
        targetAccount.setBalance(targetAccount.getBalance().add(transferMoneyDto.amount()));
        accountRepository.saveAll(List.of(sourceAccount, targetAccount));
        return accountMapper.entityToDto(sourceAccount);
    }

}


