package com.revature.projects.services;

import com.revature.projects.models.Customer;
import com.revature.projects.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

//    @BeforeEach
//    void setUp() {
//        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
//
//        Mockito.when(customerRepository.findCustomerByName("test name 1")).thenReturn((List<Customer>) customer1);
//    }

    @Test
    void insertCustomer() {
    }

    @Test
    void listAllCustomers() {
//        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
//        Customer customer2 = new Customer("test name 2", "testemail2@rb.com", "456");

//        Customer savedCustomer1 = customerRepository.save(customer1);
//        Customer savedCustomer2 = customerRepository.save(customer2);
//
//        List<Customer> allCustomers = customerRepository.findAll();

//        customerService.insertCustomer(customer1);
//        customerService.insertCustomer(customer2);
//
//        List<Customer> allCustomers = customerService.listAllCustomers();
//
//        assertThat(allCustomers).size().isGreaterThanOrEqualTo(2);
    }

    @Test
    void listCustomerById() {
        Customer customer1 = new Customer("test name 1", "testemail1@rb.com", "123");
        Customer savedCustomer = customerService.insertCustomer(customer1);

        Customer retCustomer = customerService.listCustomerById(savedCustomer.getCustomerId());

        assertThat(retCustomer.getCustomerId()).isPositive();
    }

    @Test
    void listAllCustomersByName() {
    }

    @Test
    void modifyCustomer() {
    }

    @Test
    void removeCustomer() {
    }
}