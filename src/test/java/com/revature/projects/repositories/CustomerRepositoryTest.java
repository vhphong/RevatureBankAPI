package com.revature.projects.repositories;

import com.revature.projects.models.Customer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepository customerRepository;


    // test of repository's insertCustomer
    @Test
    public void testInsertCustomer() {

        Customer customer = new Customer("TestName", "testemail@email.com", "testpassword");
        Customer savedCustomer = customerRepository.save(customer);
        Customer expectedCustomer = testEntityManager.find(Customer.class, savedCustomer.getId());

        assertNotNull(expectedCustomer);
    }

    // test of repository's listAllCustomers
    @Test
    public void testListAllCustomers() {
        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
        Customer customer2 = new Customer("test name 2", "testemail2@rb.com", "456");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers).size().isGreaterThan(0);
    }

    // test of repository's listCustomerById
    @Test
    public void testListCustomerById() {
        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
        Customer savedCustomer = customerRepository.save(customer1);

        Boolean isExisted = customerRepository.existsById(savedCustomer.getId());

        assertThat(isExisted).isEqualTo(true);
    }

    // test of repository's listCustomerById not existed
    @Test
    public void testListCustomerByIdNotExisted() {
        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
        Customer savedCustomer = customerRepository.save(customer1);

        Long fakeId = Long.valueOf(999999999);
        Boolean isExisted = customerRepository.existsById(fakeId);

        assertThat(isExisted).isEqualTo(false);
    }

    // test of repository's modifyCustomer
    @Test
    public void testModifyCustomer() {
        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
        Customer savedCustomer = customerRepository.save(customer1);

        Customer existingCustomer = testEntityManager.find(Customer.class, savedCustomer.getId());

        String newName = "test name 2";
        String newEmail = "testemail2@rb.com";
        String newPassword = "newpassword";

        existingCustomer.setName(newName);
        existingCustomer.setEmail(newEmail);
        existingCustomer.setPassword(newPassword);

        // save existingCustomer to the DB
        customerRepository.save(existingCustomer);

        assertThat(existingCustomer.getName()).isEqualTo(newName);
        assertThat(existingCustomer.getEmail()).isEqualTo(newEmail);
        assertThat(existingCustomer.getPassword()).isEqualTo(newPassword);
    }

}