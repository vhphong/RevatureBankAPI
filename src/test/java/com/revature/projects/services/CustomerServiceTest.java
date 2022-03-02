package com.revature.projects.services;

import com.revature.projects.models.Customer;
import com.revature.projects.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.verify;

public class CustomerServiceTest<customerService> {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AutoCloseable autoCloseable;

    @Mock
    private CustomerService customerService;


    @BeforeEach
    void setUp() {
        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
        customerService = new CustomerService() {
            @Override
            public Customer insertCustomer(Customer customer) {
                return null;
            }

            @Override
            public List<Customer> listAllCustomers() {
                return customerRepository.findAll();
            }

            @Override
            public Customer listCustomerById(long id) {
                return null;
            }

            @Override
            public List<Customer> listAllCustomersByName(String name) {
                return null;
            }

            @Override
            public List<Customer> retrieveCustomerByNameAndEmail(String name, String email) {
                return null;
            }

            @Override
            public Customer modifyCustomer(Customer customer, long id) {
                return null;
            }

            @Override
            public boolean removeCustomer(long id) {
                return false;
            }

            @Override
            public String welcomeCustomer() {
                return null;
            }
        };
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void insertCustomer() {
    }

    @Test
    void listAllCustomersTest() {
        // when
        customerService.listAllCustomers();
        // then
        verify(customerRepository).findAll();
    }

    @Test
    void listCustomerById() {
    }

    @Test
    void listAllCustomersByName() {
    }

    @Test
    void retrieveCustomerByNameAndEmail() {
    }

    @Test
    void modifyCustomer() {
    }

    @Test
    void removeCustomer() {
    }

    @Test
    void welcomeCustomer() {
    }
}