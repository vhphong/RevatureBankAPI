package com.revature.projects.repositories;

import com.revature.projects.models.Account;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccountCustomRepositoryImpl implements AccountCustomRepository {

    @Autowired
    private EntityManager entityManager;


    @Override
    public List<Account> findAccountBalanceGreaterThan(double lowerLimitInput) {
        String sql = "SELECT a FROM Account a WHERE a.balance >= :lowerLimit";
        final TypedQuery<Account> query = entityManager.createQuery(sql, Account.class);
        query.setParameter("lowerLimit", lowerLimitInput);

        return query.getResultList();
    }

    @Override
    public List<Account> findAccountByType(String accountTypeInput) {
        String sql = "SELECT a FROM Account a WHERE a.type = :accountType";
        final TypedQuery<Account> query = entityManager.createQuery(sql, Account.class);
        query.setParameter("accountType", accountTypeInput);

        return query.getResultList();
    }

    @Override
    public List<Account> findAccountByCustId(long customerIdInput) {
        // String sql1 = "SELECT a FROM Account a WHERE a.custId = :customerId";
        String sql2 = "SELECT a FROM Account a WHERE a.custId IN (SELECT c.customerId FROM Customer c WHERE c.customerId = :customerId)";
        final TypedQuery<Account> query = entityManager.createQuery(sql2, Account.class);
        query.setParameter("customerId", customerIdInput);

        return query.getResultList();
    }

    @Override
    public List<Account> findAccountByAccountActiveStatus(int accountActiveStatusInput) {
        String sql = "SELECT a FROM Account a WHERE a.accountActiveStatus = :accountStatus";
        final TypedQuery<Account> query = entityManager.createQuery(sql, Account.class);
        query.setParameter("accountStatus", accountActiveStatusInput);

        return query.getResultList();
    }
}
