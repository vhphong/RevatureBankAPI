package com.revature.projects.controllers;

import com.revature.projects.exceptions.BadRequestException;
import com.revature.projects.models.Account;
import com.revature.projects.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/RevBankAPI/v2/")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @GetMapping("accounts/welcome")
    public String welcomeAccounts() {
        return "Welcome to the endpoint accounts/welcome";
    }


    // to create account
    // http://localhost:8080/RevBankAPI/v2/accounts/create
    /*
     * Body's JSON:
       {
            "custId": 1,
            "balance": 0.00,
            "dateOfOpening": "2020-11-22",
            "type": "debit",
            "accountActiveStatus": true
        }
    */
    @PostMapping("accounts/create")
    public ResponseEntity<Account> saveAccount(@RequestBody Account account) {
        if (account != null) {
            return new ResponseEntity<Account>(accountService.insertAccount(account), HttpStatus.CREATED);
        } else {
            throw new BadRequestException("Request body does not contain account data");
        }
    }


    // get all accounts
    // http://localhost:8080/RevBankAPI/v2/accounts
    @GetMapping("accounts")
    public List<Account> getAllAccounts() {
        return accountService.listAllAccounts();
    }


    // get account by Id
    // http://localhost:8080/RevBankAPI/v2/accounts/id/1
    @GetMapping("accounts/id/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") long acctId) {
        return new ResponseEntity<Account>(accountService.listAccountById(acctId), HttpStatus.OK);
    }


    // get accounts that have balance greater than a low limit
    @GetMapping("accounts/balance/greater/{lowLimit}")
    public List<Account> getAccountsHaveBalanceGreaterThan(@PathVariable("lowLimit") double minValue) {
        return accountService.listAllAccountsBalanceGreaterThan(minValue);
    }


    // gets accounts by account type
    @GetMapping("accounts/type/{accountTypeInputParam}")
    public List<Account> getAccountsByAccountType(@PathVariable("accountTypeInputParam") String accountType) {
        return accountService.listAllAccountsByType(accountType);
    }


    // gets all accounts by customer ID
    @GetMapping("accounts/customer_id/{customerIdParam}")
    public List<Account> getAccountByCustId(@PathVariable("customerIdParam") long ctmrId) {
        return accountService.listAllAccountsByCustomerId(ctmrId);
    }


    // gets all accounts by customer name
    @GetMapping("accounts/customer_name/{customerNameParam}")
    public List<Account> getAccountByCustomerName(@PathVariable("customerNameParam") String ctmrName) {
        return accountService.listAllAccountsByCustomerName(ctmrName);
    }


    // get all accounts by account active status
    @GetMapping("accounts/account_status/{accountStatusParam}")
    public List<Account> getAccountByAccountActiveStatus(@PathVariable("accountStatusParam") int acctStatus) {
        return accountService.listAllAccountsByAccountActiveStatus(acctStatus);
    }


    // update account
    // http://localhost:8080/RevBankAPI/v2/accounts/2
    @PutMapping("accounts/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") long acctId,
                                                 @RequestBody Account account) {
        return new ResponseEntity<Account>(accountService.modifyAccount(account, acctId), HttpStatus.OK);
    }


    // activate account
    // http://localhost:8080/RevBankAPI/v2/accounts/activate/2
    @PatchMapping("accounts/activate/{id}")
    public ResponseEntity<Account> activateAccount(@PathVariable("id") long acctId) {
        return new ResponseEntity<Account>(accountService.enableAccount(acctId), HttpStatus.OK);
    }


    // deactivate account
    // http://localhost:8080/RevBankAPI/v2/accounts/deactivate/2
    @PatchMapping("accounts/deactivate/{id}")
    public ResponseEntity<Account> deactivateAccount(@PathVariable("id") long acctId) {
        return new ResponseEntity<Account>(accountService.disableAccount(acctId), HttpStatus.OK);
    }


    // delete account
    // http://localhost:8080/RevBankAPI/v2/accounts/delete/7
    @DeleteMapping("accounts/delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") long acctId) {
        if ((acctId > 0) && (acctId == acctId)) {
            boolean isDeleted = accountService.removeAccount(acctId);

            if (isDeleted) {
                return new ResponseEntity<String>("Account Id # " + acctId + " deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Account Id # " + acctId + " was not found to delete.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<String>("Account Id # " + acctId + " is invalid.", HttpStatus.BAD_REQUEST);
        }
    }
}