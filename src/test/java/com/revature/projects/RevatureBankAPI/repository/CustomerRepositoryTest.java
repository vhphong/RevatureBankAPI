package com.revature.projects.RevatureBankAPI.repository;

import com.revature.projects.RevatureBankAPI.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testCreateCustomer() {
        Customer customer  = new Customer("TestName","testemail@email.com", "testpassword");
        customerRepository.save(customer);
    }
}