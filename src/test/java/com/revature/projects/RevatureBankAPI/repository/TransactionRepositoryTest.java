package com.revature.projects.RevatureBankAPI.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.revature.projects.RevatureBankAPI.model.Transaction;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
public class TransactionRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	public void testCreateTransaction() {

		Transaction transaction = new Transaction();
		transaction.setType("saving");
		transaction.setAmount(4563.12);
		transaction.setStatus("active");
		transaction.setCustId(Long.valueOf(1));

		Transaction savedTransaction = transactionRepository.save(transaction);
		Transaction expectedTransaction = entityManager.find(Transaction.class, savedTransaction.getTransactionId());

		assertNotNull(expectedTransaction);

	}

	@Test
	public void testFindTransactionByTransactionId() {

		Transaction transaction = new Transaction();
		transaction.setType("saving");
		transaction.setAmount(4563.12);
		transaction.setStatus("active");
		transaction.setCustId(Long.valueOf(1));

		Transaction savedTransaction = transactionRepository.save(transaction);

		Boolean isExisted = transactionRepository.existsById(savedTransaction.getTransactionId());

		assertThat(isExisted).isEqualTo(true);
	}

	@Test
	public void testFindTransactionByTransactionIdNotExisted() {

		Transaction transaction = new Transaction();
		transaction.setType("saving");
		transaction.setAmount(4563.12);
		transaction.setStatus("active");
		transaction.setCustId(Long.valueOf(1));

		Transaction savedTransaction = transactionRepository.save(transaction);

		Transaction expectedTransaction = entityManager.find(Transaction.class, savedTransaction.getTransactionId() + 999999999);

		assertNull(expectedTransaction);

	}

	@Test
	@Rollback(value = false)
	public void testUpdateTransaction() {

		Transaction transaction = new Transaction();
		transaction.setType("saving");
		transaction.setAmount(4563.12);
		transaction.setStatus("active");
		transaction.setCustId(Long.valueOf(1));

		Transaction savedTransaction = transactionRepository.save(transaction);

		Transaction expectedTransaction = entityManager.find(Transaction.class, savedTransaction.getTransactionId());
		expectedTransaction.setAmount(888.88);

		assertThat(expectedTransaction.getAmount()).isEqualTo(888.88);

	}

	@Test
	public void testGetAllTransactions() {

		Transaction transaction = new Transaction();
		transaction.setType("saving");
		transaction.setAmount(4563.12);
		transaction.setStatus("active");
		transaction.setCustId(Long.valueOf(1));

		Transaction savedTransaction = transactionRepository.save(transaction);

		List<Transaction> transactions = transactionRepository.findAll();

		assertThat(transactions).size().isGreaterThan(0);

	}


}