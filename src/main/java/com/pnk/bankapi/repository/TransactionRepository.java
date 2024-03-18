package com.pnk.bankapi.repository;

import com.pnk.bankapi.core.TransactionStatusEnum;
import com.pnk.bankapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByReferenceId(String referenceIdInput);

    List<Transaction> findBySourceOwnerId(Long sourceOwnerIdInput);

    List<Transaction> findBySourceAccountId(Long sourceAccountIdInput);

    List<Transaction> findByDestinedOwnerId(Long destinedOwnerIdInput);

    List<Transaction> findByDestinedAccountId(Long destinedAccountIdInput);

    List<Transaction> findByAmountLessThan(BigDecimal upperThresholdInput);

    List<Transaction> findByAmountLessThanEqual(BigDecimal upperThresholdInput);

    List<Transaction> findByAmountGreaterThan(BigDecimal lowerThresholdInput);

    List<Transaction> findByAmountGreaterThanEqual(BigDecimal lowerThresholdInput);

    List<Transaction> findByAmountBetween(BigDecimal lowerThresholdInput, BigDecimal upperThresholdInput);

    List<Transaction> findByDetailStartingWith(String prefixInput);

    List<Transaction> findByDetailEndingWith(String suffixInput);

    List<Transaction> findByDetailContaining(String infixInput);

    List<Transaction> findByExecutedUTC(Date executedUTCInput);

    List<Transaction> findByExecutedUTCAfter(Date executedUTCInput);

    List<Transaction> findByExecutedUTCBefore(Date executedUTCInput);

    List<Transaction> findByScheduledUTC(Date scheduledUTCInput);

    List<Transaction> findByScheduledUTCAfter(Date scheduledUTCInput);

    List<Transaction> findByScheduledUTCBefore(Date scheduledUTCInput);

    List<Transaction> findByStatus(TransactionStatusEnum statusInput);

//    List<Transaction> findByStatusContaining(String infixStatusInput);

}
