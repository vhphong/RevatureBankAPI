package com.pnk.bankapi.service;

import com.pnk.bankapi.model.Customer;
import com.pnk.bankapi.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository mockCustomerRepository;

    @Mock
    private CustomerService mockCustomerService;

    @Mock
    private AutoCloseable autoCloseable;

    Customer customer;

    @BeforeEach
    void initUseCase() throws ParseException {
        mockCustomerService = new CustomerServiceImpl(mockCustomerRepository);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        String dateInString = "06-Mar-2013";
        Date date = formatter.parse(dateInString);

        customer = new Customer(1L, "phong", "phong@email.com", "fakepwd");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }


    @Test
    void testInsertCustomer() {
        // when
        when(mockCustomerRepository.save(customer)).thenReturn(customer);

        Customer savedCustomer = mockCustomerRepository.save(customer);
        assertThat(savedCustomer.getName()).isNotNull();

        ////////////////////////////////////////////////

/*        customerService.listAllCustomers();

        verify(customerRepository).findAll();*/
    }


    @Test
    void testListAllCustomers() {
        // when
        when(mockCustomerRepository.findAll()).thenReturn(List.of(customer));

        // then
        assertThat(mockCustomerService.listAllCustomers().size()).isPositive();
    }


    @Test
    void testListCustomerById() {
        // when
        when(mockCustomerRepository.findById(1L)).thenReturn(Optional.ofNullable(customer));

        // then
        assertThat(mockCustomerService.listCustomerById(1L)).isNotNull();
    }


    @Test
    void testListAllCustomersByName() {
        // when
        when(mockCustomerRepository.findByNameContaining(customer.getName()))
                .thenReturn(List.of(customer));
        // then
        assertThat(mockCustomerService.listAllCustomersByName(customer.getName()).size()).isEqualTo(1);
    }


    @Test
    void testListCustomerByNameAndEmail() {
        // when
        when(mockCustomerRepository.findByNameAndEmail(customer.getName(), customer.getEmail()))
                .thenReturn(List.of(customer));

        // then
        assertThat(mockCustomerService.listCustomerByNameAndEmail(customer.getName(), customer.getEmail()).size()).isPositive();
    }


    @Test
    void testRemoveCustomer() {
        // testRemoveCustomer_success
        when(mockCustomerRepository.existsById(1L)).thenReturn(true);

        mockCustomerService.removeCustomer(1L);

        // then
        verify(mockCustomerRepository).deleteById(1L);

        // testRemoveCustomerFailDueToException
        doThrow(new RuntimeException("Mocked exception")).when(mockCustomerRepository).deleteById(99L);

        boolean result = mockCustomerService.removeCustomer(99L);

        assertFalse(result);
    }
}
