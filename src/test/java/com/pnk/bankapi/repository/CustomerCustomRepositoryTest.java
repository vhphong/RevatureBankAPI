package com.pnk.bankapi.repository;

import com.pnk.bankapi.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerCustomRepositoryTest {

    Customer customer1;
    @Autowired
    private TestEntityManager testEntityManager;    // TestEntityManager is for testing customized stuff
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customer1 = new Customer(1L, "testName1", "testemail1@email.com", "testPw1");
        customerRepository.save(customer1);
    }

    @AfterEach
    void tearDown() {
        customer1 = null;
        customerRepository.deleteAll();
    }
}