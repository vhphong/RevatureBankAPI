package com.revature.projects.services;

import com.revature.projects.models.Account;

import java.util.List;

public interface AccountService {

    Account insertAccount(Account account);

    List<Account> listAllAccounts();

    Account listAccountById(long id);

    List<Account> listAllAccountsBalanceGreaterThan(double minBalance);

    List<Account> listAllAccountsByType(String accountTypeInput);

    List<Account> listAllAccountsByCustomerId(long customerIdInput);

    List<Account> listAllAccountsByCustomerName(String customerNameInput);

    List<Account> listAllAccountsByAccountActiveStatus(int accountStatus);

    Account modifyAccount(Account account, long id);

    Account enableAccount(long accountId);

    Account disableAccount(long accountId);

    boolean removeAccount(long id);
}