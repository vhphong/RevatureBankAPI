package com.revature.projects.RevatureBankAPI.controller;

import com.revature.projects.RevatureBankAPI.exceptions.RevBankResourceNotFoundException;
import com.revature.projects.RevatureBankAPI.model.Transaction;
import com.revature.projects.RevatureBankAPI.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")         // cors
@RestController
@RequestMapping("/RevBankAPI/v1/")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;


    // get all transactions
    @GetMapping("transactions")
    public List<Transaction> getAllTransactions() {

        return this.transactionRepository.findAll();

    }


    // get a transaction by transaction id
    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable(value = "id") Long transactionId)
            throws RevBankResourceNotFoundException {

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(
                () -> new RevBankResourceNotFoundException("Transaction with id: " + transactionId + " not found"));

        return ResponseEntity.ok().body(transaction);
    }


    // get all transactions of an account id


    // create a transaction
    @PostMapping("transactions")
    public Transaction createTransaction(@RequestBody Transaction transaction) {

        return this.transactionRepository.save(transaction);

    }


    // update status of a transaction
    // request body contains the new status
    /* Body 's JSON
    * {
            "type": "debit",
            "dateOfTransaction": "2020-11-22",
            "amount": "123.45",
            "status": "success",
            "fromAccountId": "1",
            "toAccountId": 2,
            "custId": 1,
            "acctId": 1
      }
    * */
    @PutMapping("/transactions/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable(value = "id") Long transactionId,
                                                         @RequestBody Transaction newerData) throws RevBankResourceNotFoundException {

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(
                () -> new RevBankResourceNotFoundException("Transaction with id: " + transactionId + " not found"));

        transaction.setStatus(newerData.getStatus());

        return ResponseEntity.ok(this.transactionRepository.save(transaction));

    }

}
