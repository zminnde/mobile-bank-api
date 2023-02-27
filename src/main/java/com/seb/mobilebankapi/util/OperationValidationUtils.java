package com.seb.mobilebankapi.util;

import com.seb.mobilebankapi.model.TransferType;
import com.seb.mobilebankapi.model.entity.Account;
import com.seb.mobilebankapi.model.entity.AccountType;

import java.math.BigDecimal;

import static com.seb.mobilebankapi.model.entity.AccountType.SAVINGS;

public final class OperationValidationUtils {

    private OperationValidationUtils() {
    }

    public static void validateWithdrawalAllowed(Account accountToUpdate, String authenticatedUserName, BigDecimal amount) {
        validateAuthorizedOperation(accountToUpdate.getCustomer().getUserName(), authenticatedUserName);
        validateAccountType(accountToUpdate.getAccountType());
        validateSufficientFunds(accountToUpdate.getBalance(), amount);
    }

    public static void validateTransferAllowed(Account sourceAccount, Account targetAccount, BigDecimal amount) {
        var transferType = resolveTransferType(sourceAccount.getId(), targetAccount.getId());
        validateTransferAmount(amount, transferType);
    }

    public static void validateAuthorizedOperation(String accountUserNameToUpdate, String authenticatedUserName) {
        if (!accountUserNameToUpdate.equals(authenticatedUserName)) {
            throw new UnsupportedOperationException("Operation not allowed");
        }
    }

    private static void validateAccountType(AccountType accountType) {
        if (SAVINGS.equals(accountType)) {
            throw new UnsupportedOperationException("Operation not allowed");
        }
    }

    private static void validateSufficientFunds(BigDecimal balance, BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    private static TransferType resolveTransferType(Long sourceCustomerId, Long targetCustomerId) {
        if (sourceCustomerId.equals(targetCustomerId)) {
            return TransferType.INTERNAL;
        }
        return TransferType.EXTERNAL;
    }

    private static void validateTransferAmount(BigDecimal amount, TransferType transferType) {
        if (amount.compareTo(transferType.getMaxAmount()) > 0) {
            throw new IllegalArgumentException("%s transfer amount cannot be higher than %s".formatted(transferType, transferType.getMaxAmount()));
        }
    }
}
