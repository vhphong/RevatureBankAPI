package com.revature.projects.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;     // patterned: tablenameId

    @Column(name = "cust_id")
    private long custId;

    @Column(name = "balance")
    private double balance;

    @Column(name = "opening_date")
    private Date dateOfOpening;

    @Column(name = "type")
    private String type;

    @Column(name = "active")
    private boolean isActive;

    public Account() {
        super();
    }

    public Account(long custId, double balance, Date dateOfOpening, String type, boolean isActive) {
        this.custId = custId;
        this.balance = balance;
        this.dateOfOpening = dateOfOpening;
        this.type = type;
        this.isActive = isActive;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getDateOfOpening() {
        return dateOfOpening;
    }

    public void setDateOfOpening(Date dateOfOpening) {
        this.dateOfOpening = dateOfOpening;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", custId=" + custId +
                ", balance=" + balance +
                ", dateOfOpening=" + dateOfOpening +
                ", type='" + type + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
