package com.revature.projects.RevatureBankAPI.repository;

import com.revature.projects.RevatureBankAPI.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testCreateCustomer() {
        Customer customer  = new Customer("TestName","testemail@email.com", "testpassword");
        Customer savedCustomer = customerRepository.save(customer);
        Customer expectedCustomer = entityManager.find(Customer.class, savedCustomer.getId());

        assertNotNull(expectedCustomer);
    }
}