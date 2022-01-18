package com.revature.projects.RevatureBankAPI.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.revature.projects.RevatureBankAPI.model.Account;

@DataJpaTest
public class AccountRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private AccountRepository accountRepository;

	@Test
	@Rollback(value = false)
	public void testCreateAccount() {

		Account account = new Account(Long.valueOf(1), 123.45, true);
		Account savedAccount = accountRepository.save(account);
		Account expectedAccount = entityManager.find(Account.class, savedAccount.getAccountId());

		assertNotNull(expectedAccount);

	}

	@Test
	public void testFindAccountByAccountId() {

		Account account = new Account(Long.valueOf(1), 123.45, true);
		Account savedAccount = accountRepository.save(account);

		Boolean isExisted = accountRepository.existsById(savedAccount.getAccountId());

		assertThat(isExisted).isEqualTo(true);

	}

	@Test
	public void testFindAccountByAccountIdNotExisted() {

		Account account = new Account(Long.valueOf(1), 123.45, true);
		Account savedAccount = accountRepository.save(account);

		Account expectedCustomer = entityManager.find(Account.class, savedAccount.getAccountId() + 999999999);

		assertNull(expectedCustomer);

	}

	@Test
	@Rollback(value = false)
	public void testUpdateAccount() {

		Account account = new Account(Long.valueOf(1), 123.45, true);
		Account savedAccount = accountRepository.save(account);

		Account expectedCustomer = entityManager.find(Account.class, savedAccount.getAccountId());
		expectedCustomer.setBalance(888.88);

		assertThat(expectedCustomer.getBalance()).isEqualTo(888.88);



	}

}