package com.pnk.bankapi.core;

import lombok.Getter;


@Getter
public enum TransactionStatusEnum {
    DELETED(-1, "DELETED"),
    UNCOMPLETED(0, "UNCOMPLETED"),
    SCHEDULED(1, "SCHEDULED"),
    PROCESSING(2, "PROCESSING"),
    COMPLETED(3, "COMPLETED"),
    DELIVERED(4, "DELIVERED");

    private final int value;
    private final String status;

    TransactionStatusEnum(int value, String status) {
        this.value = value;
        this.status = status;
    }

}
