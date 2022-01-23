package com.revature.projects.services;

import com.revature.projects.models.Account;

import java.util.List;

public interface AccountService {

    Account insertAccount(Account account);

    List<Account> listAllAccounts();

    List<Account> listAllAccountsByName(String name);

    Account listAccountById(long id);

    Account modifyAccount(Account account, long id);

    boolean removeAccount(long id);
}