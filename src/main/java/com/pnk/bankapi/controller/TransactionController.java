package com.pnk.bankapi.controller;

import com.pnk.bankapi.exception.BadRequestException;
import com.pnk.bankapi.exception.UnsupportedMediaType;
import com.pnk.bankapi.model.Transaction;
import com.pnk.bankapi.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;


@RestController
@RequestMapping("/RevBankAPI/v2/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    // create a transaction with input as below
    // http://localhost:8081/RevBankAPI/v2/transactions/create
    /*
        {
            "sourceOwnerId": 1,
            "sourceAccountId": 1,
            "destinedOwnerId": 242,
            "destinedAccountId": 2444,
            "amount": 10.00,
            "type": "DEBIT",
            "detail": "test",
            "scheduledDate": "2023-03-18",
            "status": "COMPLETED"
        }

        should return

        {
            "transactionId": "c0a83254-8ab8-10bc-818a-b880d8090000",
            "referenceId": "FYSJ4SCCRF",
            "sourceOwnerId": 1,
            "sourceAccountId": 1,
            "destinedOwnerId": 242,
            "destinedAccountId": 2444,
            "amount": 10.00,
            "type": "DEBIT",
            "detail": "test",
            "executedUTC": "2023-09-21T11:09:26.240029",
            "scheduledUTC": null,
            "status": "COMPLETED",
            "createdDate": "2023-09-21T11:09:26.317018",
            "lastUpdatedDate": "2023-09-21T11:09:26.317018"
        }
    * */
    @PostMapping("/create")
    public ResponseEntity<Transaction> saveTransaction(@Valid @RequestBody Transaction transaction) {
        if (Objects.isNull(transaction)) {
            throw new BadRequestException("Transaction data input is null.");
        }

        return new ResponseEntity<>(transactionService.insertTransaction(transaction), HttpStatus.CREATED);
    }


    // send money between two accounts of two different owners
    @PutMapping("/sendmoney")
    public ResponseEntity<Transaction> sendMoneyBetweenTwoAccounts(@Valid @RequestBody Transaction transactionBody) {
        if (Objects.isNull(transactionBody)) {
            throw new UnsupportedMediaType("Transaction data input is null.");
        }

        return new ResponseEntity<>(transactionService.sendMoneyToDifferentOwner(transactionBody), HttpStatus.OK);
    }


    // transfer money between two accounts of the same owner
    @PutMapping("/transfermoney")
    public ResponseEntity<Transaction> transferMoneyBetweenSameOwnerAccounts(@Valid @RequestBody Transaction transactionBody) {
        if (Objects.isNull(transactionBody)) {
            throw new UnsupportedMediaType("Transaction data input is null.");
        }

        return new ResponseEntity<>(transactionService.transferMoneySameOwner(transactionBody), HttpStatus.OK);
    }


    // delete transaction
    // http://localhost:8081/RevBankAPI/v2/transactions/delete/7
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable("id") UUID transactionId) {
        return (transactionService.removeTransaction(transactionId))
                ? new ResponseEntity<>("Transaction Id # " + transactionId + " deleted successfully.", HttpStatus.GONE)
                : new ResponseEntity<>("Transaction Id # " + transactionId + " is not found.", HttpStatus.NOT_FOUND);
    }
}
