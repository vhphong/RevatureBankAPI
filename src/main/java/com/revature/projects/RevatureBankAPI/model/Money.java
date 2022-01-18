package com.revature.projects.RevatureBankAPI.model;

public class Money {

    private Long amount;

    public Money() {
        super();
    }

    public Money(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
