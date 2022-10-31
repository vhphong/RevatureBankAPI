package com.revature.projects.repositories;

import com.revature.projects.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomerCustomRepositoryImpl implements CustomerCustomRepository {

    @Autowired
    private EntityManager entityManager;


    @Override
    public List<Customer> findCustomerByName(String nameInput) {
        String sql = "SELECT c FROM Customer c WHERE c.customerName = :name";
        final TypedQuery<Customer> query = entityManager.createQuery(sql, Customer.class);
        query.setParameter("name", nameInput);

        return query.getResultList();
    }

    @Override
    public List<Customer> findCustomerByEmail(String emailInput) {
        String sql = "SELECT c FROM Customer c WHERE c.customerEmail = :email";
        final TypedQuery<Customer> query = entityManager.createQuery(sql, Customer.class);
        query.setParameter("email", emailInput);

        return query.getResultList();
    }


    @Override
    public List<Customer> findCustomerByNameAndEmail(String nameInput, String emailInput) {
        String sql = "SELECT c FROM Customer c WHERE c.customerName = :name AND c.customerEmail = :email";
        final TypedQuery<Customer> query = entityManager.createQuery(sql, Customer.class);
        query.setParameter("name", nameInput);
        query.setParameter("email", emailInput);

        return query.getResultList();
    }

    @Override
    public String greetingCustomer() {
        return "Welcome, Phong!";
    }

    @Override
    public Boolean checkExistedEmail(String email) {
        String sql = "SELECT c FROM Customer c WHERE c.customerEmail = :email";
        final TypedQuery<Customer> query = entityManager.createQuery(sql, Customer.class);
        query.setParameter("email", email);

        // query.setParameter(1, email);

        int dataLen = query.getResultList().size();

        if (dataLen == 0) {
            return false;
        } else {
            return true;
        }
    }
}