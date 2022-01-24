package com.revature.projects.repositories;

import com.revature.projects.models.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AccountRepository accountRepository;


    // test of repository's insertAccount
    @Test
    public void testInsertAccount() throws ParseException {
        String sDate = "11/25/2021";
        Date date = new SimpleDateFormat("MM/dd/yyyy").parse(sDate);
        Account account = new Account(1, 123.45, date, "debit", true);

        Account savedAccount = accountRepository.save(account);
        Account expectedAccount = testEntityManager.find(Account.class, savedAccount.getAccountId());

        assertNotNull(expectedAccount);
    }


    // test of repository's listAllAccounts
    @Test
    public void testListAllAccounts() throws ParseException {
        String sDate1 = "11/25/2021";
        Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(sDate1);
        Account account1 = new Account(1, 123.45, date1, "debit", true);

        String sDate2 = "08/16/2019";
        Date date2 = new SimpleDateFormat("MM/dd/yyyy").parse(sDate2);
        Account account2 = new Account(2, 634.11, date2, "debit", false);

        accountRepository.save(account1);
        accountRepository.save(account2);

        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts).size().isGreaterThanOrEqualTo(2);
    }


    // test of repository's listAccountById
    @Test
    public void testListAccountById() throws ParseException {
        String sDate1 = "11/25/2021";
        Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(sDate1);
        Account account1 = new Account(1, 123.45, date1, "debit", true);

        Account savedAccount = accountRepository.save(account1);

        assertThat(savedAccount.getAccountId()).isPositive();
    }


    // test of repository's modifyAccount
    @Test
    public void testModifyAccount() throws ParseException {
        String sDate1 = "11/25/2021";
        Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(sDate1);
        Account account1 = new Account(1, 123.45, date1, "debit", true);

        Account savedAccount = accountRepository.save(account1);

        Account existingAccount = testEntityManager.find(Account.class, savedAccount.getAccountId());

        Long newCustId = Long.valueOf(2);
        double newBalance = 789.01;
        String sNewDate = "12/18/2020";
        Date newDate = new SimpleDateFormat("MM/dd/yyyy").parse(sNewDate);
        String newType = "saving";
        boolean newIsActive = false;

        // save existingAccount to the database
        existingAccount.setCustId(newCustId);
        existingAccount.setBalance(newBalance);
        existingAccount.setDateOfOpening(newDate);
        existingAccount.setType(newType);
        existingAccount.setIsActive(newIsActive);

        assertThat(existingAccount.getCustId()).isEqualTo(newCustId);
        assertThat(existingAccount.getBalance()).isEqualTo(newBalance);
        assertThat(existingAccount.getDateOfOpening()).isEqualTo(newDate);
        assertThat(existingAccount.getType()).isEqualTo(newType);
        assertThat(existingAccount.getIsActive()).isEqualTo(newIsActive);
    }
}
