package com.revature.projects.RevatureBankAPI.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.revature.projects.RevatureBankAPI.model.Account;

import java.util.List;

@DataJpaTest
public class AccountRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private AccountRepository accountRepository;

	@Test
	@Rollback(value = false)
	public void testCreateAccount() {

		Account account = new Account(Long.valueOf(1), 123.45, "active");
		Account savedAccount = accountRepository.save(account);
		Account expectedAccount = entityManager.find(Account.class, savedAccount.getAccountId());

		assertNotNull(expectedAccount);

	}

	@Test
	public void testFindAccountByAccountId() {

		Account account = new Account(Long.valueOf(1), 123.45, "active");
		Account savedAccount = accountRepository.save(account);

		Boolean isExisted = accountRepository.existsById(savedAccount.getAccountId());

		assertThat(isExisted).isEqualTo(true);

	}

	@Test
	public void testFindAccountByAccountIdNotExisted() {

		Account account = new Account(Long.valueOf(1), 123.45, "active");
		Account savedAccount = accountRepository.save(account);

		Account expectedAccount = entityManager.find(Account.class, Long.valueOf(999999999));

		assertNull(expectedAccount);

	}

	@Test
	@Rollback(value = false)
	public void testUpdateAccount() {

		Account account = new Account(Long.valueOf(1), 123.45, "active");
		Account savedAccount = accountRepository.save(account);

		Account expectedAccount = entityManager.find(Account.class, savedAccount.getAccountId());
		expectedAccount.setBalance(888.88);

		assertThat(expectedAccount.getBalance()).isEqualTo(888.88);

	}

	@Test
	public void testGetAllAccounts() {

		Account account = new Account(Long.valueOf(1), 123.45, "active");
		Account savedAccount = accountRepository.save(account);

		List<Account> accounts = accountRepository.findAll();

		assertThat(accounts).size().isGreaterThan(0);

	}

	@Test
	@Rollback(value = false)
	public void testDeleteAccount() {

		Account account = new Account(Long.valueOf(1), 123.45, "active");
		Account savedAccount = accountRepository.save(account);

		Long acctid = savedAccount.getAccountId();

		boolean isExistedBeforeDelete = accountRepository.findById(acctid).isPresent();

		accountRepository.deleteById(acctid);

		boolean isExistedAfterDelete = accountRepository.findById(acctid).isPresent();

		assertTrue(isExistedBeforeDelete);
		assertFalse(isExistedAfterDelete);

	}

}