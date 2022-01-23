package com.revature.projects.services;

import com.revature.projects.exceptions.ResourceNotFoundException;
import com.revature.projects.models.Account;
import com.revature.projects.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        super();
        this.accountRepository = accountRepository;
    }


    @Override
    public Account insertAccount(Account account) {
        return accountRepository.save(account);
    }


    @Override
    public List<Account> listAllAccounts() {
        return accountRepository.findAll();
    }


    @Override
    public List<Account> listAllAccountsByName(String name) {
        return null;
    }


    @Override
    public Account listAccountById(long id) {
        Optional<Account> account = accountRepository.findById(id);

        if (account.isPresent()) {
            return  account.get();
        } else {
            throw new ResourceNotFoundException("Account with Id: " + id + " was not found.");
        }

        //        return accountRepository.findById(id).orElseThrow(
//                () -> new ResourceNotFoundException("Account with Id: " + id + " was not found."));
    }


    @Override
    public Account modifyAccount(Account account, long id) {
        return null;
    }


    @Override
    public boolean removeAccount(long id) {
        return false;
    }
}
