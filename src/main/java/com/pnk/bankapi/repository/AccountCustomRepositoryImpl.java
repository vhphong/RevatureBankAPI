package com.pnk.bankapi.repository;

import com.pnk.bankapi.model.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;


public class AccountCustomRepositoryImpl implements AccountCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<Account> findAccountsBalanceGreaterThanOrEqual(BigDecimal lowerLimitInput) {
        String sql = "SELECT a FROM Account a WHERE a.accountBalance >= :lowerLimit";
        final TypedQuery<Account> query = entityManager.createQuery(sql, Account.class);
        query.setParameter("lowerLimit", lowerLimitInput);

        return query.getResultList();
    }


    @Override
    public List<Account> findAccountsBalanceLessThanOrEqual(BigDecimal upperLimitInput) {
        String sql = "SELECT a FROM Account a WHERE a.accountBalance <= :upperLimit";
        final TypedQuery<Account> query = entityManager.createQuery(sql, Account.class);
        query.setParameter("upperLimit", upperLimitInput);

        return query.getResultList();
    }


    @Override
    public List<Account> findAccountsBalanceRange(BigDecimal lowerLimitInput, BigDecimal upperLimitInput) {
        String sql = "SELECT a FROM Account a WHERE a.accountBalance >= :lowerLimit AND a.accountBalance <= :upperLimit";
        final TypedQuery<Account> query = entityManager.createQuery(sql, Account.class);
        query.setParameter("upperLimit", upperLimitInput);
        query.setParameter("lowerLimit", lowerLimitInput);

        return query.getResultList();
    }


    @Override
    public List<Account> findAccountsOwnedByCustomerNameExact(String customerNameInput) {
        String sql = "SELECT a FROM Account a WHERE a.accountCustomerID IN (SELECT c.customerId FROM Customer c WHERE c.customerName = :ctmrName)";
        final TypedQuery<Account> query = entityManager.createQuery(sql, Account.class);
        query.setParameter("ctmrName", customerNameInput);

        return query.getResultList();
    }

    @Override
    public List<Account> findAccountsOwnedByCustomerNameContaining(String customerNameInput) {
        String sql = "SELECT a FROM Account a WHERE a.accountCustomerID IN (SELECT c.customerId FROM Customer c WHERE c.customerName LIKE :ctmrName)";
        final TypedQuery<Account> query = entityManager.createQuery(sql, Account.class);
        query.setParameter("ctmrName", "%" + customerNameInput + "%");

        return query.getResultList();
    }


    @Override
    public List<Account> findAccountsOwnedByCustomerEmail(String customerEmailInput) {
        String sql = "SELECT a FROM Account a WHERE a.accountCustomerID IN (SELECT c.customerId FROM Customer c WHERE c.customerEmail = :ctmrEmail)";
        final TypedQuery<Account> query = entityManager.createQuery(sql, Account.class);
        query.setParameter("ctmrEmail", customerEmailInput);

        return query.getResultList();
    }
}