package com.pnk.bankapi.service;

import com.pnk.bankapi.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    Account insertAccount(Account accountInput);

    List<Account> listAllAccounts();

    List<Account> listAccountByAccountId(Long accountIdInput);

    List<Account> listAllAccountsBalanceGreaterThanOrEqual(BigDecimal minBalanceInput);

    List<Account> listAllAccountsBalanceLessThanOrEqual(BigDecimal maxBalanceInput);

    List<Account> listAllAccountsBalanceInRange(BigDecimal minBalanceInput, BigDecimal maxBalanceInput);

    List<Account> listAllAccountsByType(String accountTypeInput);

    List<Account> listAllAccountsByOwnerId(Long ownerIdInput);

    List<Account> listAllAccountsByCustomerName(String customerNameInput);

    List<Account> listAllAccountsByCustomerEmail(String customerEmailInput);

    List<Account> listAllAccountsByAccountActiveStatus(int accountStatusInput);

    Account modifyAccount(Account newAccount, Long accountIdInput) throws IllegalAccessException;

    Account setAccountWithStatus(Long accountIdInput, int accountStatusInput);

    boolean removeAccount(Long accountIdInput);

    Account verifyOwnershipOfAccount(Long accountOwnerIdInput, Long accountIdInput);    // verify accountIdInput belongs accountOwnerIdInput

    boolean validateTransferAmount(Long accountIdInput, BigDecimal amount);   // verify balance >= amount
}