package com.revature.projects.RevatureBankAPI.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.projects.RevatureBankAPI.exceptions.RevBankBadRequestException;
import com.revature.projects.RevatureBankAPI.model.Money;
import com.revature.projects.RevatureBankAPI.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revature.projects.RevatureBankAPI.exceptions.RevBankResourceNotFoundException;
import com.revature.projects.RevatureBankAPI.model.Account;
import com.revature.projects.RevatureBankAPI.repository.AccountRepository;

@RestController
@RequestMapping("/RevBankAPI/v1/")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    // get all accounts of all customers
    @GetMapping("accounts")
    public List<Account> getAllAccounts() {

        return this.accountRepository.findAll();

    }

    // get all accounts of a customer id
    @GetMapping("/customers/{id}/accounts")
    public ResponseEntity<Account> getAccountsByCustomerId(@PathVariable(value = "id") Long customerId)
            throws RevBankResourceNotFoundException {

        return null;

    }

    // get an account by account id
    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable(value = "id") Long accountId)
            throws RevBankResourceNotFoundException {

        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new RevBankResourceNotFoundException("Customer with id: " + accountId + " not found"));

        return ResponseEntity.ok().body(account);
    }

    // create an account
    /*
     * { "custId": 1, "balance": 0.00, "dateOfOpening": "2021-11-22", "status": true
     * }
     */
    @PostMapping("accounts")
    public Account createAccount(@RequestBody Account account) {

        return this.accountRepository.save(account);

    }

    // update status of an account
    // request body contains the new status
    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable(value = "id") Long accountId,
                                                 @RequestBody Account newerData) throws RevBankResourceNotFoundException {

        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new RevBankResourceNotFoundException("Account with id: " + accountId + " not found"));

        account.setStatus(newerData.getStatus());

        return ResponseEntity.ok(this.accountRepository.save(account));

    }

    // deposit to an account
    // http://localhost:8080/accounts/{id}/deposit
    // request body contains the amount
    @ResponseBody
    @PutMapping("/accounts/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable(value = "id") Long accountId,
                                           @RequestBody Transaction money) throws RevBankResourceNotFoundException {

        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new RevBankResourceNotFoundException("Account with id: " + accountId + " not found"));

        account.setBalance(account.getBalance() + money.getAmount());
        return ResponseEntity.ok(this.accountRepository.save(account));

    }

    // withdraw from an account
    // http://localhost:8080/accounts/{id}/withdraw
    // request body contains the amount
    @ResponseBody
    @PutMapping("/accounts/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable(value = "id") Long accountId,
                                            @RequestBody Transaction money) throws RevBankResourceNotFoundException {

        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new RevBankResourceNotFoundException("Account with id: " + accountId + " not found"));

        if (account.getBalance() >= money.getAmount()) {
            account.setBalance(account.getBalance() - money.getAmount());
        }

        return ResponseEntity.ok(this.accountRepository.save(account));

    }

    // delete an account
    @DeleteMapping("/accounts/{id}")
    public Map<String, Boolean> deleteAccount(@PathVariable(value = "id") Long accountId)
            throws RevBankResourceNotFoundException {

        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new RevBankResourceNotFoundException("Customer with id: " + accountId + " not found"));

        this.accountRepository.delete(account);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;

    }

}
