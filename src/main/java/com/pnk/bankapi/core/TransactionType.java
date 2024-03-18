package com.pnk.bankapi.core;

import lombok.Getter;


@Getter
public enum TransactionType {

    TRANSFER("TRANSFER"),
    CREDIT("CREDIT"),
    DEBIT("DEBIT");

    private final String transactionType;

    TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

}
