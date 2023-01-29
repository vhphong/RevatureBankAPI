package com.revature.projects.repositories;

import com.revature.projects.models.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerCustomRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepository customerRepository;


    // test of repository's listAllCustomersByName
    @Test
    public void findCustomersByNameTest() {
        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
        Customer savedCustomer1 = customerRepository.save(customer1);
        String customer1Name = savedCustomer1.getCustomerName();

        List<Customer> retrievedCustomerList = customerRepository.findByCustomerName(customer1Name);

        assertThat(retrievedCustomerList.size()).isGreaterThanOrEqualTo(1);
    }


    // test of repository's findCustomersByEmail
    @Test
    public void findCustomersByEmailTest() {
        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
        Customer savedCustomer1 = customerRepository.save(customer1);
        String customer1Email = customer1.getCustomerEmail();

        List<Customer> retrievedCustomerList = customerRepository.findByCustomerName(customer1Email);

        assertThat(retrievedCustomerList.size()).isGreaterThanOrEqualTo(1);
    }


    // test of repository's findCustomerByNameAndEmail
    @Test
    void findCustomerByNameAndEmailTest() {
        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
        Customer savedCustomer1 = customerRepository.save(customer1);
        String customer1Name = savedCustomer1.getCustomerName();
        String customer1Email = savedCustomer1.getCustomerEmail();

        List<Customer> retrievedCustomerList = customerRepository.findCustomerByNameAndEmail(customer1Name, customer1Email);

        Assertions.assertThat(retrievedCustomerList.size()).isGreaterThanOrEqualTo(1);
    }


    @Test
//    @Disabled
    void greetingCustomerTest() {
    }


    // test of an email is existed
    @Test
    void checkExistedEmailTest() {
        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
        customerRepository.save(customer1);

        boolean isExisted = customerRepository.existsById(customer1.getCustomerId());

        assertTrue(isExisted);
    }
}