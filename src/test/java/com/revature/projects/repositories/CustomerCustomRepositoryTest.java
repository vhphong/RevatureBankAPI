package com.revature.projects.repositories;

import com.revature.projects.models.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerCustomRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepository customerRepository;


    // test of repository's listAllCustomersByName
    @Test
    public void findCustomerByNameTest() {
        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
        customerRepository.save(customer1);

        boolean isExisted = customerRepository.findCustomerByName(customer1.getCustomerName()).isEmpty();

        assertFalse(isExisted);
    }

    @Test
    void findCustomerByNameAndEmailTest() {
    }

    @Test
    void greetingCustomerTest() {
    }

    @Test
    void checkExistedEmailTest() {
    }
}