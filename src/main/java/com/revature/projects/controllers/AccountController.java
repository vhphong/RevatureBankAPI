package com.revature.projects.controllers;

import com.revature.projects.exceptions.BadRequestException;
import com.revature.projects.models.Account;
import com.revature.projects.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/RevBankAPI/v2/")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        super();
        this.accountService = accountService;
    }


    // build create account REST API
    /*
        {
            "custId": 1,
            "balance": 0.00,
            "dateOfOpening": "2020-11-22",
            "type": "debit",
            "isActive": true
        }

        http://localhost:8080/RevBankAPI/v2/accounts
    */
    @PostMapping("accounts")
    public ResponseEntity<Account> saveAccount(@RequestBody Account account) {
        if (account != null) {
            return new ResponseEntity<Account>(accountService.insertAccount(account), HttpStatus.CREATED);
        } else {
            throw new BadRequestException("Request body does not contain customer data");
        }
    }


    // build get all accounts REST API
    // http://localhost:8080/RevBankAPI/v2/accounts
    @GetMapping("accounts")
    public List<Account> getAllAccounts() {
        return accountService.listAllAccounts();
    }


    // build get account by Id REST API
    // http://localhost:8080/RevBankAPI/v2/accounts/1
    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") long acctId) {
        return new ResponseEntity<Account>(accountService.listAccountById(acctId), HttpStatus.OK);
    }


    // build update account REST API
    // http://localhost:8080/RevBankAPI/v2/accounts/17
    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") long acctId,
                                                 @RequestBody Account account) {
        return new ResponseEntity<Account>(accountService.modifyAccount(account, acctId), HttpStatus.OK);
    }


    // build delete account REST API
    // http://localhost:8080/RevBankAPI/v2/accounts/21
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") long acctId) {
        if (acctId > 0) {
            boolean isDeleted = accountService.removeAccount(acctId);

            if (isDeleted) {
                return new ResponseEntity<String>("Account id: " + acctId + " deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Account id: " + acctId + " was not found to delete.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<String>("Account id: " + acctId + " is invalid.", HttpStatus.BAD_REQUEST);
        }
    }
}