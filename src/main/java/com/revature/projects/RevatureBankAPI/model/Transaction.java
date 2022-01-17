package com.revature.projects.RevatureBankAPI.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;

	@Column(name = "type")
	private String type;

	@Column(name = "transaction_date")
	private Date dateOfTransaction;

	@Column(name = "amount")
	private double amount;

	@Column(name = "status")
	private String status;

	@Column(name = "from_account")
	private Long fromAccount;

	@Column(name = "to_account")
	private Long toAccount;

	@Column(name = "customer_id")
	private Long custId; // fk

	@Column(name = "account_id")
	private Long acctId; // fk

	public Transaction() {
		super();
	}

	public Transaction(String type, Date dateOfTransaction, double amount, String status, Long fromAccount,
			Long toAccount, Long custId, Long acctId) {
		this.type = type;
		this.dateOfTransaction = dateOfTransaction;
		this.amount = amount;
		this.status = status;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.custId = custId;
		this.acctId = acctId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Long fromAccount) {
		this.fromAccount = fromAccount;
	}

	public Long getToAccount() {
		return toAccount;
	}

	public void setToAccount(Long toAccount) {
		this.toAccount = toAccount;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Long getAcctId() {
		return acctId;
	}

	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", type=" + type + ", dateOfTransaction="
				+ dateOfTransaction + ", amount=" + amount + ", status=" + status + ", fromAccount=" + fromAccount
				+ ", toAccount=" + toAccount + ", custId=" + custId + ", acctId=" + acctId + "]";
	}

}
