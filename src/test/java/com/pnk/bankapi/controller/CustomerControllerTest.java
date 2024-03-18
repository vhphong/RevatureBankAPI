package com.pnk.bankapi.controller;

import com.pnk.bankapi.dto.CustomerDTO;
import com.pnk.bankapi.exception.UnsupportedMediaType;
import com.pnk.bankapi.model.Account;
import com.pnk.bankapi.model.Customer;
import com.pnk.bankapi.repository.CustomerRepository;
import com.pnk.bankapi.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class CustomerControllerTest {

    @MockBean
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    Customer customer;

    CustomerDTO customerDTO;

    @BeforeEach
    void setUp() throws ParseException {
        customerService = mock(CustomerService.class);
        customerController = new CustomerController(customerService);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        String dateInString = "06-Mar-2022";
        Date date = formatter.parse(dateInString);
        customer = new Customer(2L, "john", List.of(new Account()));
    }

    @AfterEach
    void tearDown() {
        customer = null;
        customerRepository = null;
        customerService = null;
        customerController = null;
    }


    @Test
    void testSaveCustomer_ValidData_Success() {
        // Arrange
        CustomerService customerService = mock(CustomerService.class);
        when(customerService.insertCustomer(any(Customer.class))).thenReturn(customer);
        CustomerController customerController = new CustomerController(customerService);
        // Act
        ResponseEntity<Customer> response = customerController.saveCustomer(customer);
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customer, response.getBody());
        verify(customerService, times(1)).insertCustomer(customer);
    }


    @Test
    void testSaveCustomer_NullData_BadRequestException() {
        when(customerService.insertCustomer(null)).thenThrow(UnsupportedMediaType.class);
        // Act and Assert
        assertThrows(UnsupportedMediaType.class, () -> {
            customerController.saveCustomer(null);
        });
        verify(customerService, never()).insertCustomer(any(Customer.class));
    }


    @Test
    void testGetAllCustomers() {
        when(customerService.insertCustomer(any(Customer.class))).thenReturn(customer);
        when(customerService.listAllCustomers()).thenReturn(List.of(customer));
        // Act
        ResponseEntity<List<Customer>> response = customerController.getFilteredCustomers("", null, null, null);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(customer), response.getBody());

        verify(customerService, times(1)).listAllCustomers();
    }


    @Test
    void testGetCustomerById() {
        when(customerService.insertCustomer(any(Customer.class))).thenReturn(customer);
        when(customerService.listCustomerById(1L)).thenReturn(List.of(customer));
        // Act
        ResponseEntity<List<Customer>> response = customerController.getFilteredCustomers(null, 1L, null, null);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(customer), response.getBody());
        verify(customerService, times(1)).listCustomerById(1L);
    }


    @Test
    void testGetCustomerByName() {
        when(customerService.insertCustomer(any(Customer.class))).thenReturn(customer);
        when(customerService.listAllCustomersByName("Phong")).thenReturn(List.of(customer));
        // Act
        ResponseEntity<List<Customer>> response = customerController.getFilteredCustomers(null, null, "Phong", null);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(customer), response.getBody());
        verify(customerService, times(1)).listAllCustomersByName("Phong");
    }


    @Test
    void testGetCustomerByEmail() {
        when(customerService.insertCustomer(any(Customer.class))).thenReturn(customer);
        when(customerService.listAllCustomersByEmail("test@email.com")).thenReturn(List.of(customer));
        // Act
        ResponseEntity<List<Customer>> response = customerController.getFilteredCustomers(null, null, null, "test@email.com");
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(customer), response.getBody());
        verify(customerService, times(1)).listAllCustomersByEmail("test@email.com");
    }


    @Test
    void testGetCustomerByNameAndEmail() {
        when(customerService.insertCustomer(any(Customer.class))).thenReturn(customer);
        when(customerService.listCustomerByNameAndEmail("Phong", "test@email.com")).thenReturn(List.of(customer));
        // Act
        ResponseEntity<List<Customer>> response = customerController.getFilteredCustomers(null, null, "Phong", "test@email.com");
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(customer), response.getBody());
        verify(customerService, times(1)).listCustomerByNameAndEmail("Phong", "test@email.com");
    }


    @Test
    void testUpdateCustomer() {
        when(customerService.modifyCustomer(customerDTO, 1L)).thenReturn(customer);
        // Act
        ResponseEntity<Customer> response = customerController.updateCustomer(1L, customerDTO);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(customerService, times(1)).modifyCustomer(customerDTO, 1L);
    }


    @Test
    void testDeleteCustomer() {
        when(customerService.insertCustomer(any(Customer.class))).thenReturn(customer);
        // test case: delete an existing customer ID
        when(customerService.removeCustomer(1L)).thenReturn(true);
        // Act
        ResponseEntity<String> response = customerController.deleteCustomer(1L);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // test case: delete a non-existing customer ID
        when(customerService.removeCustomer(99L)).thenReturn(false);
        // Act
        ResponseEntity<String> response1 = customerController.deleteCustomer(99L);
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
        verify(customerService, times(1)).removeCustomer(1L);
        verify(customerService, times(1)).removeCustomer(99L);
        verify(customerService, times(2)).removeCustomer(any(Long.class));
    }


    @Test
    void testConfirmEmailIsTaken() {
        // test case: verify an existing email
        when(customerService.verifyEmailIfTaken("test@email.com")).thenReturn(true);
        // Act
        boolean response = customerController.confirmEmailIsTaken("test@email.com");
        // Assert
        assertTrue(response);

        // test case: verify a non-existing email
        when(customerService.verifyEmailIfTaken("dne@email.com")).thenReturn(false);
        // Act
        response = customerController.confirmEmailIsTaken("dne@email.com");
        // Assert
        assertFalse(response);
        verify(customerService, times(1)).verifyEmailIfTaken("test@email.com");
        verify(customerService, times(1)).verifyEmailIfTaken("dne@email.com");
        verify(customerService, times(2)).verifyEmailIfTaken(any(String.class));
    }


    @Test
    void testGreeting() {
        String greetingStr = "Welcome to the endpoint customers/welcome";
        when(customerService.greet()).thenReturn(greetingStr);
        // Act
        String response = customerController.welcomeCustomers();
        // Assert
        assertEquals(greetingStr, response);
    }
}
