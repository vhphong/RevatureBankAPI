package com.pnk.bankapi.service;

import com.pnk.bankapi.core.TransactionStatusEnum;
import com.pnk.bankapi.model.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    Transaction insertTransaction(Transaction transactionInput);

    List<Transaction> listTransactionsByTransactionId(UUID transactionIdInput);

    List<Transaction> listAllTransactions();

    List<Transaction> listTransactionsByReferenceId(String referenceIdInput);

    List<Transaction> listTransactionsBySourceOwnerId(Long sourceOwnerIdInput);

    List<Transaction> listTransactionsBySourceAccountId(Long sourceAccountIdInput);

    List<Transaction> listTransactionsByDestinedOwnerId(Long destinedOwnerIdInput);

    List<Transaction> listTransactionsByDestinedAccountId(Long destinedAccountIdInput);

    List<Transaction> listTransactionsByAmountLessThan(BigDecimal upperThresholdInput);

    List<Transaction> listTransactionsByAmountLessThanEqual(BigDecimal upperThresholdInput);

    List<Transaction> listTransactionsByAmountGreaterThan(BigDecimal lowerThresholdInput);

    List<Transaction> listTransactionsByAmountGreaterThanEqual(BigDecimal lowerThresholdInput);

    List<Transaction> listTransactionsByAmountBetween(BigDecimal lowerThresholdInput, BigDecimal upperThresholdInput);

    List<Transaction> listTransactionsByDetailStartingWith(String prefixInput);

    List<Transaction> listTransactionsByDetailEndingWith(String suffixInput);

    List<Transaction> listTransactionsByDetailContaining(String infixInput);

    List<Transaction> listTransactionsOnExecutedDate(Date executedDateInput);

    List<Transaction> listTransactionsOnExecutedDateAfter(Date executedDateInput);

    List<Transaction> listTransactionsOnExecutedDateBefore(Date executedDateInput);

    List<Transaction> listTransactionsOnScheduledDate(Date scheduledDateInput);

    List<Transaction> listTransactionsByScheduledDateAfter(Date scheduledDateInput);

    List<Transaction> listTransactionsByScheduledDateBefore(Date scheduledDateInput);

    List<Transaction> listTransactionsByStatus(TransactionStatusEnum statusInput);

//    List<Transaction> listTransactionsByStatusContaining(TransactionStatusEnum infixStatusInput);

    Transaction modifyTransaction(Transaction newTransaction, UUID oldTransactionIdInput) throws NoSuchFieldException, IllegalAccessException;

    Transaction modifyTransactionStatus(UUID transactionIdInput, TransactionStatusEnum transactionStatusEnumInput);

    Transaction sendMoneyToDifferentOwner(Transaction transaction);

    Transaction transferMoneySameOwner(Transaction transaction);

    boolean removeTransaction(UUID transactionIdInput);
}
