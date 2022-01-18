package com.revature.projects.RevatureBankAPI.repository;

import com.revature.projects.RevatureBankAPI.model.Account;
import org.checkerframework.checker.units.qual.Temperature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Rollback(value = false)
    public void testCreateAccount() {

        Account account = new Account(Long.valueOf(1),123.45, true);
        Account savedAccount = accountRepository.save(account);
        Account expectedAccount = entityManager.find(Account.class, savedAccount.getAccountId());

        assertNotNull(expectedAccount);

    }

}