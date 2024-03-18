package com.pnk.bankapi.repository;

import com.pnk.bankapi.model.Account;
import com.pnk.bankapi.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class AccountCustomRepositoryImplTest {
    Account account1, account2;
    Customer customer1, customer2;
    @Mock
    private EntityManager entityManager;
    @Mock
    private TypedQuery<Account> query;
    @InjectMocks
    private AccountCustomRepositoryImpl accountCustomRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this); // cause entityManager is null if not using

        account1 = new Account();
        account1.setAccountId(1L);
        account1.setBalance(new BigDecimal("1200"));
        customer1 = new Customer();
        customer1.setName("Emmanuel");
        customer1.setEmail("emmanuel@revbank.com");
        account1.setCustomer(customer1);

        account2 = new Account();
        account2.setAccountId(2L);
        account2.setBalance(new BigDecimal("1500"));
        customer2 = new Customer();
        customer2.setName("Emma");
        account2.setCustomer(customer2);
    }

    @After
    public void tearDown() throws Exception {
        account1 = null;
        account2 = null;
        customer1 = null;
        customer2 = null;
    }

    @Test
    public void testFindAccountsBalanceGreaterThanOrEqual() {
        BigDecimal lowerLimitInput = new BigDecimal("1000");

        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        // Mock the behavior of the EntityManager and TypedQuery
        String sql = "SELECT a FROM Account a WHERE a.accountBalance >= :lowerLimit";
        when(entityManager.createQuery(sql, Account.class)).thenReturn(query);
        when(query.setParameter("lowerLimit", lowerLimitInput)).thenReturn(query);
        when(query.getResultList()).thenReturn(accounts);

        // Call the method to be tested
        List<Account> result = accountCustomRepository.findAccountsBalanceGreaterThanOrEqual(lowerLimitInput);

        // Assert the result
        assertEquals(accounts, result);
        assertEquals(2, result.size());

        // Verify that the EntityManager and TypedQuery were called with the correct parameters
        verify(entityManager).createQuery(sql, Account.class);
        verify(query).setParameter("lowerLimit", lowerLimitInput);
        verify(query).getResultList();
    }


    @Test
    public void testFindAccountsBalanceLessThanOrEqual() {
        BigDecimal upperLimitInput = new BigDecimal("2000");

        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        // Mock the behavior of the EntityManager and TypedQuery
        String sql = "SELECT a FROM Account a WHERE a.accountBalance <= :upperLimit";
        when(entityManager.createQuery(sql, Account.class)).thenReturn(query);
        when(query.setParameter("upperLimit", upperLimitInput)).thenReturn(query);
        when(query.getResultList()).thenReturn(accounts);

        // Call the method to be tested
        List<Account> result = accountCustomRepository.findAccountsBalanceLessThanOrEqual(upperLimitInput);

        // Assert the result
        assertEquals(accounts, result);
        assertEquals(2, result.size());

        // Verify that the EntityManager and TypedQuery were called with the correct parameters
        verify(entityManager).createQuery(sql, Account.class);
        verify(query).setParameter("upperLimit", upperLimitInput);
        verify(query).getResultList();
    }


    @Test
    public void testFindAccountsBalanceRange() {
        BigDecimal lowerLimitInput = new BigDecimal("1000");
        BigDecimal upperLimitInput = new BigDecimal("2000");

        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        // Mock the behavior of the EntityManager and TypedQuery
        String sql = "SELECT a FROM Account a WHERE a.accountBalance >= :lowerLimit AND a.accountBalance <= :upperLimit";
        when(entityManager.createQuery(sql, Account.class)).thenReturn(query);
        when(query.setParameter("upperLimit", upperLimitInput)).thenReturn(query);
        when(query.setParameter("lowerLimit", lowerLimitInput)).thenReturn(query);
        when(query.getResultList()).thenReturn(accounts);

        // Call the method to be tested
        List<Account> result = accountCustomRepository.findAccountsBalanceRange(lowerLimitInput, upperLimitInput);

        // Assert the result
        assertEquals(accounts, result);
        assertEquals(2, result.size());

        // Verify that the EntityManager and TypedQuery were called with the correct parameters
        verify(entityManager).createQuery(sql, Account.class);
        verify(query).setParameter("upperLimit", upperLimitInput);
        verify(query).setParameter("lowerLimit", lowerLimitInput);
        verify(query).getResultList();
    }


    @Test
    public void testFindAccountsOwnedByCustomerNameExact() {
        String queryingNameInput = "Emm";   // like Emma, Emmanuel

        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        // Mock the behavior of the EntityManager and TypedQuery
        String sql = "SELECT a FROM Account a WHERE a.accountCustomerID IN (SELECT c.customerId FROM Customer c WHERE c.customerName = :ctmrName)";
        when(entityManager.createQuery(sql, Account.class)).thenReturn(query);
        when(query.setParameter("ctmrName", queryingNameInput)).thenReturn(query);
        when(query.getResultList()).thenReturn(accounts);

        // Call the method to be tested
        List<Account> result = accountCustomRepository.findAccountsOwnedByCustomerNameExact(queryingNameInput);

        // Assert the result
        assertEquals(accounts, result);
        assertEquals(2, result.size());
        assertEquals(account1, result.get(0));
        assertEquals(customer1.getName(), result.get(0).getCustomer().getName());

        // Verify that the EntityManager and TypedQuery were called with the correct parameters
        verify(entityManager).createQuery(sql, Account.class);
        verify(query).setParameter("ctmrName", queryingNameInput);
        verify(query).getResultList();
    }


    @Test
    public void testFindAccountsOwnedByCustomerNameContaining() {
        String queryingNameInput = "Emm";   // like Emma, Emmanuel

        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        // Mock the behavior of the EntityManager and TypedQuery
        String sql = "SELECT a FROM Account a WHERE a.accountCustomerID IN (SELECT c.customerId FROM Customer c WHERE c.customerName LIKE :ctmrName)";
        when(entityManager.createQuery(sql, Account.class)).thenReturn(query);
        when(query.setParameter("ctmrName", queryingNameInput)).thenReturn(query);
        when(query.getResultList()).thenReturn(accounts);

        // Call the method to be tested
        List<Account> result = accountCustomRepository.findAccountsOwnedByCustomerNameContaining(queryingNameInput);

        // Assert the result
        assertEquals(accounts, result);
        assertEquals(2, result.size());
        assertEquals(account1, result.get(0));
        assertEquals(customer1.getName(), result.get(0).getCustomer().getName());

        // Verify that the EntityManager and TypedQuery were called with the correct parameters
        verify(entityManager).createQuery(sql, Account.class);
        verify(query).setParameter("ctmrName", "%" + queryingNameInput + "%");
        verify(query).getResultList();
    }


    @Test
    public void testFindAccountsOwnedByCustomerEmail() {
        String queryingEmailInput = "emmanuel@revbank.com";

        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);

        // Mock the behavior of the EntityManager and TypedQuery
        String sql = "SELECT a FROM Account a WHERE a.accountCustomerID IN (SELECT c.customerId FROM Customer c WHERE c.customerEmail = :ctmrEmail)";
        when(entityManager.createQuery(sql, Account.class)).thenReturn(query);
        when(query.setParameter("ctmrEmail", queryingEmailInput)).thenReturn(query);
        when(query.getResultList()).thenReturn(accounts);

        // Call the method to be tested
        List<Account> result = accountCustomRepository.findAccountsOwnedByCustomerEmail(queryingEmailInput);

        // Assert the result
        assertEquals(accounts, result);
        assertEquals(1, result.size());

        // Verify that the EntityManager and TypedQuery were called with the correct parameters
        verify(entityManager).createQuery(sql, Account.class);
        verify(query).setParameter("ctmrEmail", queryingEmailInput);
        verify(query).getResultList();
    }
}
