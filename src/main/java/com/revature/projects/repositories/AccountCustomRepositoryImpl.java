package com.revature.projects.repositories;

import com.revature.projects.models.Account;
import com.revature.projects.models.Customer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccountCustomRepositoryImpl implements AccountCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;


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
        String sql2 = "SELECT a FROM Account a WHERE a.custId IN (SELECT c.customerId FROM Customer c WHERE c.customerId = :ctmrId)";
        final TypedQuery<Account> query = entityManager.createQuery(sql2, Account.class);
        query.setParameter("ctmrId", customerIdInput);

        return query.getResultList();
    }

    @Override
    public List<Account> findAccountByCustomerName(String customerNameInput) {
//        String sql = "SELECT a FROM Account a WHERE a.custId = (SELECT c.customerId FROM Customer c WHERE c.customerName LIKE :ctmrName)";
//        final TypedQuery<Account> query = entityManager.createQuery(sql, Account.class);
//        query.setParameter("ctmrName", "%" + customerNameInput + "%");
//
//        return query.getResultList();

/////////////////////////////////

        String sql1 = "SELECT c FROM Customer c WHERE c.customerName LIKE :name";
        final TypedQuery<Customer> query1 = entityManager.createQuery(sql1, Customer.class);
        query1.setParameter("name", "%" + customerNameInput + "%");

        long retrievedCustomerId = query1.getSingleResult().getCustomerId();
//        long retrievedCustomerId = retrievedCustomer.getCustomerId();

        String sql2 = "SELECT a FROM Account a WHERE a.custId = 2";
        final TypedQuery<Account> query2 = entityManager.createQuery(sql2, Account.class);
//        query2.setParameter("custid", retrievedCustomerId);

        return query2.getResultList();
    }


    @Override
    public List<Account> findAccountByAccountActiveStatus(int accountActiveStatusInput) {
        String sql = "SELECT a FROM Account a WHERE a.accountActiveStatus = :accountStatus";
        final TypedQuery<Account> query = entityManager.createQuery(sql, Account.class);
        query.setParameter("accountStatus", accountActiveStatusInput);

        return query.getResultList();
    }
}
