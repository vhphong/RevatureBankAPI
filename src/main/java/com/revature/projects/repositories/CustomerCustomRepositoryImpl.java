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
    public List<Customer> findCustomerByName(String name) {

        String sql = "SELECT c from Customer c WHERE c.customerName=:name";
        final TypedQuery<Customer> query = entityManager.createQuery(sql, Customer.class);
        query.setParameter("name", name);

        return query.getResultList();
    }
}