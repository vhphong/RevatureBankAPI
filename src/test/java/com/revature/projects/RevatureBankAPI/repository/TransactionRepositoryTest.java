package com.revature.projects.RevatureBankAPI.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.revature.projects.RevatureBankAPI.model.Transaction;

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

}