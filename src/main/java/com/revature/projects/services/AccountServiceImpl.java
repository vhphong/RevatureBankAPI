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
    public Account insertAccount(Account accountInput) {
        return accountRepository.save(accountInput);
    }


    @Override
    public List<Account> listAllAccounts() {
        return accountRepository.findAll();
    }


    @Override
    public List<Account> listAllAccountsBalanceGreaterThan(double minBalanceInput) {
        return accountRepository.findAccountBalanceGreaterThan(minBalanceInput);
    }

    @Override
    public List<Account> listAllAccountsByType(String accountTypeInput) {
        return accountRepository.findAccountByType(accountTypeInput);
    }

    @Override
    public List<Account> listAllAccountsByCustomerId(long customerIdInput) {
        return accountRepository.findAccountByCustId(customerIdInput);
    }

    @Override
    public List<Account> listAllAccountsByCustomerName(String customerNameInput) {
        return accountRepository.findAccountByCustomerName(customerNameInput);
    }

    @Override
    public List<Account> listAllAccountsByAccountActiveStatus(int accountStatus) {
        return accountRepository.findAccountByAccountActiveStatus(accountStatus);
    }


    @Override
    public Account listAccountById(long accountIdInput) {
        Optional<Account> account = accountRepository.findById(accountIdInput);

        if (account.isPresent()) {
            return account.get();
        } else {
            throw new ResourceNotFoundException("Account with Id: " + accountIdInput + " was not found.");
        }

        //        return accountRepository.findById(id).orElseThrow(
//                () -> new ResourceNotFoundException("Account with Id: " + accountIdInput + " was not found."));
    }


    @Override
    public Account modifyAccount(Account account, long accountIdInput) {
        // check whether account with given id is existing in the database or not
        Account existingAccount = accountRepository.findById(accountIdInput).orElseThrow(
                () -> new ResourceNotFoundException("Account with Id: " + accountIdInput + " was not found."));

        existingAccount.setCustId(account.getCustId());
        existingAccount.setBalance(account.getBalance());
        existingAccount.setDateOfOpening(account.getDateOfOpening());
        existingAccount.setType(account.getType());
        existingAccount.setAccountActiveStatus(account.getAccountActiveStatus());

        // save existingAccount to the database
        accountRepository.save(existingAccount);

        return existingAccount;
    }

    @Override
    public Account enableAccount(long accountIdInput) {
        Account retrievedAccount = accountRepository.findById(accountIdInput).orElseThrow(
                () -> new ResourceNotFoundException("Account with Id: " + accountIdInput + " was not found."));
        retrievedAccount.setAccountActiveStatus(1);
        accountRepository.save(retrievedAccount);

        return retrievedAccount;
    }

    @Override
    public Account disableAccount(long accountIdInput) {
        Account retrievedAccount = accountRepository.findById(accountIdInput).orElseThrow(
                () -> new ResourceNotFoundException("Account with Id: " + accountIdInput + " was not found."));
        retrievedAccount.setAccountActiveStatus(0);
        accountRepository.save(retrievedAccount);

        return retrievedAccount;
    }

    @Override
    public boolean removeAccount(long accountIdInput) {
        try {
            accountRepository.deleteById(accountIdInput);
            return true;
        } catch (Exception error) {
            System.out.println(error.getMessage());
            return false;
        }
    }
}
