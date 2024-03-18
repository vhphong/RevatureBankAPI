package com.pnk.bankapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;     // patterned: tablenameId

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "opening_date")
    @CreationTimestamp
    @PastOrPresent
    private LocalDateTime openingDate;

    @Column(name = "last_updated_date")
    @PastOrPresent
    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;

    @Column(name = "type")
    private String type;

    @Column(name = "active_status")
    @Min(value = 0, message = "Active status should be 0 or greater.")
    @Max(value = 99, message = "Active status should be 99 or less.")
    private int activeStatus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "customerId"/*, nullable = false*/)
    @JsonIgnoreProperties("accountList")
    private Customer customer;

    public Account(Long accountId, BigDecimal balance, Customer customer) {
        this.accountId = accountId;
        this.balance = balance;
        this.customer = customer;
    }

    public Account(Long accountId, BigDecimal balance, String type, int activeStatus) {
        this.accountId = accountId;
        this.balance = balance;
        this.type = type;
        this.activeStatus = activeStatus;
    }

    public Account(Long accountId, BigDecimal balance, String type, int activeStatus, Customer customer) {
        this.accountId = accountId;
        this.balance = balance;
        this.type = type;
        this.activeStatus = activeStatus;
        this.customer = customer;
    }
}