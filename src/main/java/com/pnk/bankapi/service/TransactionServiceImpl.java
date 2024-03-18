package com.pnk.bankapi.service;

import com.pnk.bankapi.core.TransactionStatusEnum;
import com.pnk.bankapi.exception.BadRequestException;
import com.pnk.bankapi.exception.ResourceNotFoundException;
import com.pnk.bankapi.exception.UnsupportedMediaType;
import com.pnk.bankapi.model.Account;
import com.pnk.bankapi.model.Transaction;
import com.pnk.bankapi.repository.AccountRepository;
import com.pnk.bankapi.repository.CustomerRepository;
import com.pnk.bankapi.repository.TransactionRepository;
import com.pnk.bankapi.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.pnk.bankapi.core.TransactionStatusEnum.*;


@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Value("${server.port}")
    public int serverPort;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction insertTransaction(Transaction transactionInput) {
        if (Objects.isNull(transactionInput)) {
            throw new UnsupportedMediaType("Transaction input is null.");
        }

        transactionInput.setReferenceId(Utils.generateRandomStringAZ09(10));
        transactionInput.setCreatedDate(LocalDateTime.now());
        transactionInput.setLastUpdatedDate(LocalDateTime.now());
        transactionInput.setExecutedUTC(LocalDateTime.now());

        return transactionRepository.save(transactionInput);
    }

    @Override
    public List<Transaction> listTransactionsByTransactionId(UUID transactionIdInput) {
        if (transactionIdInput == null) {
            throw new UnsupportedMediaType("Transaction Id input is null.");
        }

        Transaction retrievedTransaction = transactionRepository.findById(transactionIdInput).orElseThrow(
                () -> new ResourceNotFoundException("Transaction Id " + transactionIdInput + " is not found.")
        );

        return List.of(retrievedTransaction);
    }


    @Override
    public List<Transaction> listAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> listTransactionsByReferenceId(String referenceIdInput) {
        return transactionRepository.findByReferenceId(referenceIdInput);
    }


    @Override
    public List<Transaction> listTransactionsBySourceOwnerId(Long sourceOwnerIdInput) {
        return transactionRepository.findBySourceOwnerId(sourceOwnerIdInput);
    }


    @Override
    public List<Transaction> listTransactionsBySourceAccountId(Long sourceAccountIdInput) {
        return transactionRepository.findBySourceAccountId(sourceAccountIdInput);
    }


    @Override
    public List<Transaction> listTransactionsByDestinedOwnerId(Long destinedOwnerIdInput) {
        return transactionRepository.findByDestinedOwnerId(destinedOwnerIdInput);
    }


    @Override
    public List<Transaction> listTransactionsByDestinedAccountId(Long destinedAccountIdInput) {
        return transactionRepository.findByDestinedAccountId(destinedAccountIdInput);
    }


    @Override
    public List<Transaction> listTransactionsByAmountLessThan(BigDecimal upperThresholdInput) {
        return transactionRepository.findByAmountLessThan(upperThresholdInput);
    }


    @Override
    public List<Transaction> listTransactionsByAmountLessThanEqual(BigDecimal upperThresholdInput) {
        return transactionRepository.findByAmountLessThanEqual(upperThresholdInput);
    }


    @Override
    public List<Transaction> listTransactionsByAmountGreaterThan(BigDecimal lowerThresholdInput) {
        return transactionRepository.findByAmountGreaterThan(lowerThresholdInput);
    }


    @Override
    public List<Transaction> listTransactionsByAmountGreaterThanEqual(BigDecimal lowerThresholdInput) {
        return transactionRepository.findByAmountGreaterThanEqual(lowerThresholdInput);
    }


    @Override
    public List<Transaction> listTransactionsByAmountBetween(BigDecimal lowerThresholdInput, BigDecimal upperThresholdInput) {
        return transactionRepository.findByAmountBetween(lowerThresholdInput, upperThresholdInput);
    }


    @Override
    public List<Transaction> listTransactionsByDetailStartingWith(String prefixInput) {
        return transactionRepository.findByDetailStartingWith(prefixInput);
    }


    @Override
    public List<Transaction> listTransactionsByDetailEndingWith(String suffixInput) {
        return transactionRepository.findByDetailEndingWith(suffixInput);
    }


    @Override
    public List<Transaction> listTransactionsByDetailContaining(String infixInput) {
        return transactionRepository.findByDetailContaining(infixInput);
    }


    @Override
    public List<Transaction> listTransactionsOnExecutedDate(Date executedDateInput) {
        return transactionRepository.findByExecutedUTC(executedDateInput);
    }


    @Override
    public List<Transaction> listTransactionsOnExecutedDateAfter(Date executedDateInput) {
        return transactionRepository.findByExecutedUTCAfter(executedDateInput);
    }


    @Override
    public List<Transaction> listTransactionsOnExecutedDateBefore(Date executedDateInput) {
        return transactionRepository.findByExecutedUTCBefore(executedDateInput);
    }


    @Override
    public List<Transaction> listTransactionsOnScheduledDate(Date scheduledDateInput) {
        return transactionRepository.findByScheduledUTC(scheduledDateInput);
    }


    @Override
    public List<Transaction> listTransactionsByScheduledDateAfter(Date scheduledDateInput) {
        return transactionRepository.findByScheduledUTCAfter(scheduledDateInput);
    }


    @Override
    public List<Transaction> listTransactionsByScheduledDateBefore(Date scheduledDateInput) {
        return transactionRepository.findByScheduledUTCBefore(scheduledDateInput);
    }


    @Override
    public List<Transaction> listTransactionsByStatus(TransactionStatusEnum statusInput) {
        return transactionRepository.findByStatus(statusInput);
    }

//    @Override
//    public List<Transaction> listTransactionsByStatusContaining(TransactionStatusEnum infixStatusInput) {
//        return transactionRepository.findByStatusContaining(infixStatusInput.toString());
//    }


    @Override
    public Transaction modifyTransaction(Transaction newTransaction, UUID oldTransactionIdInput) {
        Transaction retrievedTransaction = transactionRepository.findById(oldTransactionIdInput).orElseThrow(
                () -> new ResourceNotFoundException("Transaction with Id: " + oldTransactionIdInput + " is not found."));

        if (!Objects.equals(newTransaction.getReferenceId(), retrievedTransaction.getReferenceId())
                && Objects.nonNull(newTransaction.getReferenceId())
                && !Objects.equals(newTransaction.getReferenceId(), "")) {
            retrievedTransaction.setReferenceId(Objects.requireNonNull(newTransaction.getReferenceId()));
        }

        if (Objects.nonNull(newTransaction.getSourceOwnerId())
                && !Objects.equals(newTransaction.getSourceOwnerId(), retrievedTransaction.getSourceOwnerId())) {
            retrievedTransaction.setSourceOwnerId(Objects.requireNonNull(newTransaction.getSourceOwnerId()));
        }

        if (Objects.nonNull(newTransaction.getSourceAccountId())
                && !Objects.equals(newTransaction.getSourceAccountId(), retrievedTransaction.getSourceAccountId())) {
            retrievedTransaction.setSourceAccountId(Objects.requireNonNull(newTransaction.getSourceAccountId()));
        }

        if (Objects.nonNull(newTransaction.getDestinedOwnerId())
                && !Objects.equals(newTransaction.getDestinedOwnerId(), retrievedTransaction.getDestinedOwnerId())) {
            retrievedTransaction.setDestinedOwnerId(Objects.requireNonNull(newTransaction.getDestinedOwnerId()));
        }

        if (Objects.nonNull(newTransaction.getDestinedAccountId())
                && !Objects.equals(newTransaction.getDestinedAccountId(), retrievedTransaction.getDestinedAccountId())) {
            retrievedTransaction.setDestinedAccountId(Objects.requireNonNull(newTransaction.getDestinedAccountId()));
        }

        if (Objects.nonNull(newTransaction.getAmount())
                && !Objects.equals(newTransaction.getAmount(), retrievedTransaction.getAmount())) {
            retrievedTransaction.setAmount(Objects.requireNonNull(newTransaction.getAmount()));
        }

        if (Objects.nonNull(newTransaction.getType())
                && !Objects.equals(newTransaction.getType(), retrievedTransaction.getType())) {
            retrievedTransaction.setType(Objects.requireNonNull(newTransaction.getType()));
        }

        if (Objects.nonNull(newTransaction.getDetail())
                && !Objects.equals(newTransaction.getDetail(), retrievedTransaction.getDetail())
                && !Objects.equals(newTransaction.getDetail(), "")) {
            retrievedTransaction.setDetail(Objects.requireNonNull(newTransaction.getDetail()));
        }

        if (Objects.nonNull(newTransaction.getExecutedUTC())
                && !Objects.equals(newTransaction.getExecutedUTC(), retrievedTransaction.getExecutedUTC())) {
            retrievedTransaction.setExecutedUTC(Objects.requireNonNull(newTransaction.getExecutedUTC()));
        }

        if (Objects.nonNull(newTransaction.getScheduledUTC())
                && !Objects.equals(newTransaction.getScheduledUTC(), retrievedTransaction.getScheduledUTC())) {
            retrievedTransaction.setScheduledUTC(Objects.requireNonNull(newTransaction.getScheduledUTC()));
        }

        if (Objects.nonNull(newTransaction.getStatus())) {
            retrievedTransaction.setStatus(Objects.requireNonNull(newTransaction.getStatus()));
        }

        retrievedTransaction.setLastUpdatedDate(LocalDateTime.now());

        return retrievedTransaction;
    }


    @Override
    public Transaction modifyTransactionStatus(UUID transactionIdInput, TransactionStatusEnum transactionStatusEnumInput) {
        Transaction retrievedTransaction = transactionRepository.findById(transactionIdInput).orElseThrow(
                () -> new ResourceNotFoundException("Transaction with Id: " + transactionIdInput + " is not found."));

        retrievedTransaction.setStatus(transactionStatusEnumInput);

        return retrievedTransaction;
    }


    @Override
    public Transaction sendMoneyToDifferentOwner(Transaction transaction) {
        Long sourceOwnerId = transaction.getSourceOwnerId();
        Long sourceAccountId = transaction.getSourceAccountId();
        Long destinedOwnerId = transaction.getDestinedOwnerId();
        Long destinedAccountId = transaction.getDestinedAccountId();
        BigDecimal amount = transaction.getAmount();

        Account sourceAccount = accountService.verifyOwnershipOfAccount(sourceOwnerId, sourceAccountId);
        Account destinedAccount = accountService.verifyOwnershipOfAccount(destinedOwnerId, destinedAccountId);

        if (Objects.nonNull(sourceAccount) && Objects.nonNull(destinedAccount)
                && accountService.validateTransferAmount(sourceAccountId, amount)) {
            sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
            destinedAccount.setBalance(destinedAccount.getBalance().add(amount));
            accountRepository.save(sourceAccount);
            accountRepository.save(destinedAccount);

            transaction.setReferenceId(Utils.generateRandomStringAZ09(10));
            transaction.setExecutedUTC(LocalDateTime.now());
            transaction.setStatus(COMPLETED);
            transaction.setLastUpdatedDate(LocalDateTime.now());
        } else {
            transaction.setStatus(UNCOMPLETED);
            throw new BadRequestException("Transaction could not be executed.");
        }

        return transactionRepository.save(transaction);
    }


    @Override
    public Transaction transferMoneySameOwner(Transaction transaction) {
        if (transaction.getDestinedOwnerId().equals(transaction.getSourceOwnerId())) {
            return sendMoneyToDifferentOwner(transaction);
        } else {
            transaction.setStatus(UNCOMPLETED);
            throw new BadRequestException("Transaction could not be executed.");
        }
    }


    @Override
    public boolean removeTransaction(UUID transactionIdInput) {
        if (transactionIdInput != null && transactionRepository.existsById(transactionIdInput)) {
            try {
                modifyTransactionStatus(transactionIdInput, DELETED);
                transactionRepository.deleteById(transactionIdInput);
                return true;
            } catch (Exception error) {
                System.out.println(error.getMessage());
                return false;
            }
        }
        throw new ResourceNotFoundException("Transaction ID " + transactionIdInput + " is not found.");
    }
}
