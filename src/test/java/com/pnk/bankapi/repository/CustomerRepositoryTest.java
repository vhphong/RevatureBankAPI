package com.pnk.bankapi.repository;

import com.pnk.bankapi.model.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Disabled
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

    Customer customer1, customer2;
    @Autowired
    private TestEntityManager testEntityManager;    // TestEntityManager is for testing customized stuff
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customer1 = new Customer();
        customer1.setCustomerId(1L);
        customer1.setName("testName1");
        customer1.setEmail("testemail1@email.com");
        customer1.setPassword("testPw1");

        customer2 = new Customer();
        customer2.setCustomerId(2L);
        customer2.setName("testName2");
        customer2.setEmail("testemail2@email.com");
        customer2.setPassword("testPw2");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }

    @AfterEach
    void tearDown() {
        customer1 = null;
        customer2 = null;

        customerRepository.deleteAll();
    }


    // test of repository's insertCustomer
    @Test
    void testInsertCustomer() {
        Customer retrievedCustomer1 = testEntityManager.find(Customer.class, customer1.getCustomerId());

        assertNotNull(testEntityManager);
    }


    // test of repository's listAllCustomers
    @Test
    void testListAllCustomers() {
        customerRepository.saveAll(List.of(customer1, customer2));

        List<Customer> retrievedAllCustomers = customerRepository.findAll();

        assertThat(retrievedAllCustomers).size().isGreaterThanOrEqualTo(2);
    }


    // test of repository's listCustomerById
    @Test
    void testListCustomerById() {
        customerRepository.save(customer1);

        Optional<Customer> retrievedCustomer = customerRepository.findById(customer1.getCustomerId());

        assertThat(retrievedCustomer).isNotNull();
    }


    // test of repository's listCustomerById not existing
    @Test
    void testListCustomerByIdNotExisting() {
        Long fakeId = 999999999L;
        boolean isExisting = customerRepository.existsById(fakeId);

        assertThat(isExisting).isEqualTo(false);
    }


    // test of repository's modifyCustomer
    @Disabled
    @Test
    @Rollback(value = true)
    void testModifyCustomer() {
        Customer existingCustomer = testEntityManager.find(Customer.class, customer1.getCustomerId());

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


    // test of repository's removeCustomer
    @Disabled
    @Test
    @Rollback(value = false)
    void testRemoveCustomer() {
        Long cId = customer1.getCustomerId();

        boolean isExistedBeforeRemove = customerRepository.findById(cId).isPresent();

        customerRepository.deleteById(cId);

        boolean isExistedAfterRemove = customerRepository.findById(cId).isPresent();

        assertTrue(isExistedBeforeRemove);
        assertFalse(isExistedAfterRemove);
    }


    @Test
    @Rollback(value = false)
    void testFindByCustomerName_Found() {
        List<Customer> customerList = customerRepository.findByName(customer1.getName());

        assertThat(customerList.size()).isGreaterThanOrEqualTo(1);
        assertThat(customerList.get(0).getName()).isEqualTo(customer1.getName());
        assertThat(customerList.get(0).getEmail()).isEqualTo(customer1.getEmail());
        assertThat(customerList.get(0).getPassword()).isEqualTo(customer1.getPassword());
    }


    @Test
    @Rollback(value = false)
    void testFindByCustomerName_NotFound() {
        List<Customer> customerList = customerRepository.findByName("differentName");

        assertTrue(customerList.isEmpty());
    }


    @Test
    @Rollback(value = false)
    void testFindByCustomerEmail_Found() {
        List<Customer> customerList = customerRepository.findByEmail(customer1.getEmail());

        assertThat(customerList.size()).isGreaterThanOrEqualTo(1);
        assertThat(customerList.get(0).getName()).isEqualTo(customer1.getName());
        assertThat(customerList.get(0).getEmail()).isEqualTo(customer1.getEmail());
        assertThat(customerList.get(0).getPassword()).isEqualTo(customer1.getPassword());
    }


    @Test
    @Rollback(value = false)
    void testFindByCustomerEmail_NotFound() {
        List<Customer> customerList = customerRepository.findByEmail("differentEmail");

        assertTrue(customerList.isEmpty());
    }


    @Test
    @Rollback(value = false)
    void testFindByCustomerNameAndCustomerEmail() {
        String customer1Name = customer1.getName();
        String customer1Email = customer1.getEmail();

        List<Customer> customerList = customerRepository.findByNameAndEmail(customer1Name, customer1Email);

        Assertions.assertThat(customerList.size()).isGreaterThanOrEqualTo(1);
        assertThat(customerList.get(0).getName()).isEqualTo(customer1.getName());
        assertThat(customerList.get(0).getEmail()).isEqualTo(customer1.getEmail());
        assertThat(customerList.get(0).getPassword()).isEqualTo(customer1.getPassword());
    }
}