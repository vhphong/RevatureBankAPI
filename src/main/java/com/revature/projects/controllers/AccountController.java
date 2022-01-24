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
    @GetMapping("accounts")
    public List<Account> getAllAccounts() {
        return accountService.listAllAccounts();
    }
}