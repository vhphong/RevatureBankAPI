package com.revature.projects.services;

import com.revature.projects.models.Account;

import java.util.List;

public interface AccountService {

    Account insertAccount(Account accountInput);

    List<Account> listAllAccounts();

    Account listAccountById(long accountIdInput);

    List<Account> listAllAccountsBalanceGreaterThan(double minBalanceInput);

    List<Account> listAllAccountsByType(String accountTypeInput);

    List<Account> listAllAccountsByCustomerId(long customerIdInput);

    List<Account> listAllAccountsByCustomerName(String customerNameInput);

    List<Account> listAllAccountsByAccountActiveStatus(int accountStatus);

    Account modifyAccount(Account account, long accountIdInput);

    Account enableAccount(long accountIdInput);

    Account disableAccount(long accountIdInput);

    boolean removeAccount(long accountIdInput);
}