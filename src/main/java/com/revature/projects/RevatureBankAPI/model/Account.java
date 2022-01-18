package com.revature.projects.RevatureBankAPI.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;

	@Column(name = "cust_id")
	private Long custId;

	@Column(name = "balance")
	private double balance;

	@Column(name = "opening_date")
	private Date dateOfOpening;

	@Column(name = "status")
	private String status;

	public Account() {
		super();
	}

	public Account(Long custId, double balance, String status) {
		this.custId = custId;
		this.balance = balance;
		this.status = status;
	}

	public Account(Long accountId, Long custId, double balance, Date dateOfOpening, String status) {
		this.accountId = accountId;
		this.custId = custId;
		this.balance = balance;
		this.dateOfOpening = dateOfOpening;
		this.status = status;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
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
		return "Account [accountId=" + accountId + ", custId=" + custId + ", balance=" + balance + ", dateOfOpening="
				+ dateOfOpening + ", status=" + status + "]";
	}

}
