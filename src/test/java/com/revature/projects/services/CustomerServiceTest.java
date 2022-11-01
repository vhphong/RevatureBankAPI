package com.revature.projects.services;

import com.revature.projects.models.Customer;
import com.revature.projects.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

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
                return customerRepository.save(customer);
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
            public List<Customer> listAllCustomersByEmail(String email) {
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
            public Customer deactivateCustomerAccount(long customerid) {
                return null;
            }

            @Override
            public boolean removeCustomer(long id) {
                customerRepository.deleteById(id);

                return true;
            }

            @Override
            public boolean checkEmailIfTaken(String email) {
                return customerRepository.checkExistedEmail(email);
            }
        };
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void insertCustomerTest() {
        // given
        Customer customer = new Customer("Test Name", "testemail@email.com", "123");

        // when
        customerService.insertCustomer(customer);

        // then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerRepository).save(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();


        assertThat(capturedCustomer).isEqualTo(customer);
    }

    @Test
    void listAllCustomersTest() {
        // when
        customerService.listAllCustomers();

        // then
        verify(customerRepository).findAll();
    }

    @Test
    @Disabled
    void listCustomerByIdTest() {
    }

    @Test
    @Disabled
    void listAllCustomersByName() {
    }

    @Test
    @Disabled
    void retrieveCustomerByNameAndEmail() {
    }

    @Test
    @Disabled
    void modifyCustomer() {
    }

    @Test
    void removeCustomerTest() {
        // when
        customerService.removeCustomer(1);

        // then
        verify(customerRepository).deleteById(Long.valueOf(1));
    }

    @Test
    void checkEmailIfTakenTest() {
        // when
        customerService.checkEmailIfTaken("test_email@email.com");

        // then
        verify(customerRepository).checkExistedEmail("test_email@email.com");
    }
}