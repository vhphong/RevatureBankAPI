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

    @Column(name = "status")
    private String status;

    public Account() {
        super();
    }

    public Account(Long custId, double balance, Date dateOfOpening, String status) {
        this.custId = custId;
        this.balance = balance;
        this.dateOfOpening = dateOfOpening;
        this.status = status;
    }

    public Long getId() {
        return accountId;
    }

    public void setId(Long id) {
        this.accountId = id;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", custId=" + custId +
                ", balance=" + balance +
                ", dateOfOpening=" + dateOfOpening +
                ", status='" + status + '\'' +
                '}';
    }
}
