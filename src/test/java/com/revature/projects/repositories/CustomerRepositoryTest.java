package com.revature.projects.repositories;

import com.revature.projects.models.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepository customerRepository;

    // test of repository's insertCustomer
    @Test
    public void insertCustomerTest() {
        Customer customer1 = new Customer("Test Name 1", "testname1@email.com", "testpassword1");
        Customer saveCustomer1 = customerRepository.save(customer1);
        Customer expectedCustomer1 = testEntityManager.find(Customer.class, saveCustomer1.getCustomerId());

        assertNotNull(expectedCustomer1);
    }


    // test of repository's listAllCustomers
    @Test
    public void listAllCustomersTest() {
        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
        Customer customer2 = new Customer("test name 2", "testemail2@rb.com", "456");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers).size().isGreaterThanOrEqualTo(2);
    }


    // test of repository's listCustomerById
    @Test
    public void listCustomerByIdTest() {
        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
        Customer savedCustomer = customerRepository.save(customer1);

        Boolean isExisted = customerRepository.existsById(savedCustomer.getCustomerId());

        assertThat(isExisted).isEqualTo(true);
    }


    // test of repository's listCustomerById not existing
    @Test
    public void listCustomerByIdNotExistingTest() {
        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
        Customer savedCustomer = customerRepository.save(customer1);

        Long fakeId = Long.valueOf(999999999);
        Boolean isExisting = customerRepository.existsById(fakeId);

        assertThat(isExisting).isEqualTo(false);
    }


    // test of repository's modifyCustomer
    @Test
    @Rollback(value = false)
    public void modifyCustomerTest() {
        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
        Customer savedCustomer = customerRepository.save(customer1);

        Customer existingCustomer = testEntityManager.find(Customer.class, savedCustomer.getCustomerId());

        String newName = "test name 2";
        String newEmail = "testemail2@rb.com";
        String newPassword = "newpassword";

        existingCustomer.setCustomerName(newName);
        existingCustomer.setCustomerEmail(newEmail);
        existingCustomer.setCustomerPassword(newPassword);

        // save existingCustomer to the DB
        customerRepository.save(existingCustomer);

        assertThat(existingCustomer.getCustomerName()).isEqualTo(newName);
        assertThat(existingCustomer.getCustomerEmail()).isEqualTo(newEmail);
        assertThat(existingCustomer.getCustomerPassword()).isEqualTo(newPassword);
    }


    // test of repository's removeCustomer
    @Test
    @Rollback(value = false)
    public void removeCustomerTest() {
        Customer customer = new Customer("test name 1", "testemail1@rb.com", "123");
        Customer savedCustomer = customerRepository.save(customer);

        Long cId = customer.getCustomerId();

        Boolean isExistedBeforeRemove = customerRepository.findById(cId).isPresent();

        customerRepository.deleteById(cId);

        Boolean isExistedAfterRemove = customerRepository.findById(cId).isPresent();

        assertTrue(isExistedBeforeRemove);
        assertFalse(isExistedAfterRemove);
    }


    // test of repository's
}
