package com.pnk.bankapi.model;

import com.pnk.bankapi.core.TransactionStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID transactionId;     // patterned: tableNameId

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "source_owner_id")
    @NotNull
    private Long sourceOwnerId;

    @Column(name = "source_account_id")
    @NotNull
    private Long sourceAccountId;

    @Column(name = "destined_owner_id")
    @NotNull
    private Long destinedOwnerId;

    @Column(name = "destined_account_id")
    @NotNull
    private Long destinedAccountId;

    @Column(name = "amount")
    @NotNull
    @Positive
    private BigDecimal amount;

    @Column(name = "type")
    @NotNull
    private String type;

    @Column(name = "detail")
    private String detail;

    @Column(name = "executed_date")
    private LocalDateTime executedUTC;

    @Column(name = "scheduled_date")
    private LocalDateTime scheduledUTC;

    @Column(name = "status")
    private TransactionStatusEnum status;

    @Column(name = "created_date")
    @CreationTimestamp
    @PastOrPresent
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date")
    @PastOrPresent
    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;

    public Transaction(UUID transactionId, @NotNull String referenceId, @NotNull Long sourceOwnerId, @NotNull Long sourceAccountId, @NotNull Long destinedOwnerId, @NotNull Long destinedAccountId, @NotNull @Positive BigDecimal amount) {
        this.transactionId = transactionId;
        this.referenceId = referenceId;
        this.sourceOwnerId = sourceOwnerId;
        this.sourceAccountId = sourceAccountId;
        this.destinedOwnerId = destinedOwnerId;
        this.destinedAccountId = destinedAccountId;
        this.amount = amount;
    }

    public Transaction(@NotNull Long sourceOwnerId, @NotNull Long sourceAccountId, @NotNull Long destinedOwnerId, @NotNull Long destinedAccountId, @NotNull @Positive BigDecimal amount) {
        this.sourceOwnerId = sourceOwnerId;
        this.sourceAccountId = sourceAccountId;
        this.destinedOwnerId = destinedOwnerId;
        this.destinedAccountId = destinedAccountId;
        this.amount = amount;
    }

}