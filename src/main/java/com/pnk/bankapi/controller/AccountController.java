package com.pnk.bankapi.controller;

import com.pnk.bankapi.exception.BadRequestException;
import com.pnk.bankapi.model.Account;
import com.pnk.bankapi.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/RevBankAPI/v2/accounts")
public class AccountController {

    @Value("${server.port}")
    public int serverPort;

    @Autowired
    private AccountService accountService;


    @GetMapping("/welcome")
    public String welcomeAccounts() {
        return "Hello from the endpoint accounts/welcome. The application is listening on PORT: " + serverPort;
    }


    // to create account
    // http://localhost:8081/RevBankAPI/v2/accounts/create
    /*
     * Body's JSON input:
        {
            "balance": 400.00,
            "type": "credit",
            "activeStatus": 6,
            "customer": {
                "customerId": 2
            }
        }

        should return

        {
            "accountId": 1,
            "balance": 400.00,
            "openingDate": "2023-09-09T15:14:54.006484",
            "lastUpdatedDate": "2023-09-09T15:14:54.005484",
            "type": "credit",
            "activeStatus": 6,
            "customer": {
                "customerId": 2,
                "name": "Jane",
                "email": "jane@test.com",
                "dob": "2002-12-16T00:00:00.000+00:00",
                "phone": "1-800-fake",
                "address": "USA",
                "password": "faKePw2",
                "createdDate": "2023-09-09T19:39:31.362+00:00",
                "lastUpdatedDate": "2023-09-09T19:39:31.362+00:00"
            }
        }
    */
    @PostMapping("/create")
    public ResponseEntity<Account> saveAccount(@Valid @RequestBody Account account) {
        if (Objects.nonNull(account)) {
            return new ResponseEntity<>(accountService.insertAccount(account), HttpStatus.CREATED);
        } else {
            throw new BadRequestException("Request body does not contain account data.");
        }
    }


    // http://localhost:8081/RevBankAPI/v2/accounts?id=6
    // http://localhost:8081/RevBankAPI/v2/accounts?maxBalance=4268.49
    // http://localhost:8081/RevBankAPI/v2/accounts?minBalance=3143.83
    // http://localhost:8081/RevBankAPI/v2/accounts?minBalance=2000.31&maxBalance=3000.16
    // http://localhost:8081/RevBankAPI/v2/accounts?all
    // http://localhost:8081/RevBankAPI/v2/accounts?type=debit
    // http://localhost:8081/RevBankAPI/v2/accounts?activeStatus=6
    // http://localhost:8081/RevBankAPI/v2/accounts?customerId=1234
    // http://localhost:8081/RevBankAPI/v2/accounts?customerName=Phong
    // http://localhost:8081/RevBankAPI/v2/accounts?customerEmail=phong@email.com
    @GetMapping("")
    public List<Account> getAccountsWithGenericParameters(
            @RequestParam(value = "id", required = false) Long accountId,
            @RequestParam(value = "minBalance", required = false) BigDecimal minBalance,
            @RequestParam(value = "maxBalance", required = false) BigDecimal maxBalance,
            @RequestParam(value = "type", required = false) String accountType,
            @RequestParam(value = "customerId", required = false) Long customerId,
            @RequestParam(value = "customerName", required = false) String customerName,
            @RequestParam(value = "customerEmail", required = false) String customerEmail,
            @RequestParam(value = "activeStatus", required = false) Integer activeStatus,
            @RequestParam(value = "all", required = false) String allAccounts) {
        if (allAccounts != null) {
            return accountService.listAllAccounts();
        }

        if (accountId != null) {
            return accountService.listAccountByAccountId(accountId);
        }

        if (activeStatus != null) {
            return accountService.listAllAccountsByAccountActiveStatus(activeStatus);
        }

        if (customerId != null) {
            return accountService.listAllAccountsByOwnerId(customerId);
        }

        if (customerEmail != null) {
            return accountService.listAllAccountsByCustomerEmail(customerEmail);
        }

        if (customerName != null) {
            return accountService.listAllAccountsByCustomerName(customerName);
        }

        if (accountType != null) {
            return accountService.listAllAccountsByType(accountType);
        }

        if (minBalance == null && maxBalance == null) {
            return Collections.emptyList();
        }

        if (minBalance == null) {
            return accountService.listAllAccountsBalanceLessThanOrEqual(maxBalance);
        }

        if (maxBalance == null) {
            return accountService.listAllAccountsBalanceGreaterThanOrEqual(minBalance);
        }

        return accountService.listAllAccountsBalanceInRange(minBalance, maxBalance);
    }


    // update account
    // http://localhost:8081/RevBankAPI/v2/accounts?id=2
    @PutMapping("")
    public ResponseEntity<Account> updateAccount(@RequestParam("id") Long acctId, @RequestBody Account account) throws IllegalAccessException {
        return new ResponseEntity<>(accountService.modifyAccount(account, acctId), HttpStatus.OK);
    }


    // activate/deactivate account
    // http://localhost:8081/RevBankAPI/v2/accounts/status?activate=2
    // http://localhost:8081/RevBankAPI/v2/accounts/status?deactivate=2
    // http://localhost:8081/RevBankAPI/v2/accounts/status?activate=2&deactivate=5
    // http://localhost:8081/RevBankAPI/v2/accounts/status?
    @PatchMapping("/status")
    public ResponseEntity<Account> setAccountActiveStatus(@RequestParam(value = "activate", required = false) Long acctIdToActivate,
                                                          @RequestParam(value = "deactivate", required = false) Long accIdToDeact) {
        if (acctIdToActivate != null && accIdToDeact == null) {
            return new ResponseEntity<>(accountService.setAccountWithStatus(acctIdToActivate, 1), HttpStatus.OK);
        }

        if (acctIdToActivate == null && accIdToDeact != null) {
            return new ResponseEntity<>(accountService.setAccountWithStatus(accIdToDeact, 0), HttpStatus.OK);
        }

        throw new BadRequestException("RequestParam should not be appeared/null at the same time.");
    }


    // delete account
    // http://localhost:8081/RevBankAPI/v2/accounts/delete/7
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Long acctId) {
        boolean isDeleted = accountService.removeAccount(acctId);

        if (isDeleted) {
            return new ResponseEntity<>("Account Id # " + acctId + " deleted successfully.", HttpStatus.GONE);
        } else {
            return new ResponseEntity<>("Account Id # " + acctId + " is not found.", HttpStatus.NOT_FOUND);
        }
    }
}