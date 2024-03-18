package com.pnk.bankapi.service;

import com.pnk.bankapi.dto.CustomerDTO;
import com.pnk.bankapi.exception.ResourceNotFoundException;
import com.pnk.bankapi.exception.UnsupportedMediaType;
import com.pnk.bankapi.model.Customer;
import com.pnk.bankapi.repository.CustomerRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class CustomerServiceImplTest {

    @Value("${server.port}")
    public int serverPort;
    @Mock
    AutoCloseable autoCloseable;
    Customer customer1;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        mock(Customer.class);
        mock(CustomerRepository.class);

        autoCloseable = MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository);
        customer1 = new Customer(1L, "testName1", "testemail1@email.com", "testPw1");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testInsertCustomer() {
        // Success
        customer1.setPassword("");
        when(customerRepository.findByName(anyString())).thenReturn(new ArrayList<>());
        when(customerRepository.findByEmail(anyString())).thenReturn(new ArrayList<>());
        when(customerRepository.save(customer1)).thenReturn(customer1);

        Customer result = customerService.insertCustomer(customer1);

        assertEquals(customer1, result);
        verify(customerRepository, times(1)).save(customer1);

        // testInsertCustomer_DuplicateName/Email_ThrowsException
        List<Customer> existingCustomers2 = new ArrayList<>();
        existingCustomers2.add(customer1);

        when(customerRepository.findByEmail(customer1.getEmail())).thenReturn(existingCustomers2);

        assertThrows(UnsupportedMediaType.class, () -> customerService.insertCustomer(customer1));

        // testInsertCustomer_InvalidEmail
        customer1.setEmail("username.@domain.com");

        assertThrows(UnsupportedMediaType.class, () -> customerService.insertCustomer(customer1));
    }


    @Test
    void testListAllCustomers() {
        when(customerRepository.findAll()).thenReturn(new ArrayList<>(Collections.singleton(customer1)));

        AssertionsForClassTypes.assertThat(customerService.listAllCustomers().get(0).getName()).isEqualTo(customer1.getName());
    }


    @Test
    void testListCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(ofNullable((customer1)));

        AssertionsForClassTypes.assertThat(customerService.listCustomerById(1L).get(0).getName()).isEqualTo(customer1.getName());

        // test case: whenCustomerDoesNotExist_shouldThrowResourceNotFoundException
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.listCustomerById(99L));
    }


    @Test
    void testListAllCustomersByName() {
//        when(customerRepository.findByCustomerNameContaining(customer1.getCustomerName())).thenReturn(new ArrayList<>(Collections.singleton(customer1)));         // good
        when(customerRepository.findByNameContaining(customer1.getName())).thenReturn(List.of(customer1));

        AssertionsForClassTypes.assertThat(customerService.listAllCustomersByName(customer1.getName()).get(0).getCustomerId()).isEqualTo(customer1.getCustomerId());
    }


    @Test
    void testListAllCustomersByEmail() {
//        when(customerRepository.findByCustomerEmail(customer1.getCustomerEmail()))
//                .thenReturn(new ArrayList<>(Collections.singleton(customer1)));          // good
        when(customerRepository.findByEmail(customer1.getEmail())).thenReturn(List.of(customer1));

        AssertionsForClassTypes.assertThat(customerService.listAllCustomersByEmail(customer1.getEmail()).get(0).getCustomerId()).isEqualTo(customer1.getCustomerId());
    }


    @Test
    void testListCustomerByNameAndEmail() {
//        when(customerRepository.findByCustomerNameAndCustomerEmail("testName1", "testemail1@email.com"))
//                .thenReturn(new ArrayList<>(Collections.singleton(customer1)));             // good
        when(customerRepository.findByNameAndEmail("testName1", "testemail1@email.com")).thenReturn(List.of(customer1));

        AssertionsForClassTypes.assertThat(customerService.listCustomerByNameAndEmail("testName1", "testemail1@email.com").get(0).getCustomerId()).isEqualTo(customer1.getCustomerId());
    }


    @Test
    void testModifyCustomer() {
        Long customerId = 1L;

        CustomerDTO customer1_modified = new CustomerDTO();
        customer1_modified.setName("Jane Smith");
        customer1_modified.setAddress("456 Oak St");
        customer1_modified.setDob(LocalDate.of(2021, 5, 22));
        customer1_modified.setPhone("987-654-3210");
        customer1_modified.setEmail("jane.smith@example.com");
        customer1_modified.setPassword("newpassword");

        // Mock repository behavior
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer1));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);

        // Call the method
        Customer result = customerService.modifyCustomer(customer1_modified, customerId);

        // Verify the modifications
        assertEquals(customer1_modified.getName(), result.getName());
        assertEquals(customer1_modified.getAddress(), result.getAddress());
        assertEquals(customer1_modified.getDob(), result.getDob());
        assertEquals(customer1_modified.getPhone(), result.getPhone());
        assertEquals(customer1_modified.getEmail(), result.getEmail());
        assertEquals(customer1_modified.getPassword(), result.getPassword());

        // Verify that the save method was called
        verify(customerRepository, times(1)).save(customer1);

        // test case: non-existing customer
        assertThrows(ResourceNotFoundException.class, () -> customerService.modifyCustomer(new CustomerDTO(), 99L));
    }


    @Test
    void testDeactivateCustomerAccount() {
    }


    @Test
    void testRemoveCustomer() {
        // Success
        Long customerId1 = 1L;

        doNothing().when(customerRepository).deleteById(customerId1);

        boolean result1 = customerService.removeCustomer(customerId1);

        assertTrue(result1);
        verify(customerRepository, times(1)).deleteById(customerId1);

        // Failure
        Long customerId2 = 2L;

        String errorMessage = "Failed to delete customer";
        doThrow(new RuntimeException(errorMessage)).when(customerRepository).deleteById(customerId2);

        boolean result2 = customerService.removeCustomer(customerId2);

        assertFalse(result2);
        verify(customerRepository, times(1)).deleteById(customerId2);
    }


    @Test
    void testVerifyEmailIfTaken() {
//        when(customerRepository.findByCustomerEmail("testemail1@email.com"))
//                .thenReturn(new ArrayList<>(Collections.singleton(customer1)));     // good
        when(customerRepository.findByEmail(customer1.getEmail())).thenReturn(List.of(customer1));

        assertThat(customerService.verifyEmailIfTaken(customer1.getEmail())).isEqualTo(true);
    }

    @Test
    void testVerifyPhoneIfTaken() {
//        when(customerRepository.findByCustomerEmail("testemail1@email.com"))
//                .thenReturn(new ArrayList<>(Collections.singleton(customer1)));     // good
        when(customerRepository.findByPhone("1-test-phone")).thenReturn(List.of(customer1));

        assertThat(customerService.verifyPhoneIfTaken("1-test-phone")).isEqualTo(true);
    }


    @Test
    void testVerifyCustomerExisting() {
        when(customerRepository.findById(1L)).thenReturn(ofNullable(customer1));

        assertTrue(customerService.verifyCustomerExisting(1L));
    }


    @Test
    void testGreet() {
        assertEquals("Welcome to the endpoint customers/welcome. The application is listening on PORT: " + serverPort, customerService.greet());
    }


    @Test
    void testResetPassword() {
        String s = customerService.resetPassword(customer1.getCustomerId());

        assertNotNull(s);
        assertEquals(8, s.length());
    }


    @Test
    void testChangePassword() {
        // test case: existing customer
        when(customerRepository.findById(customer1.getCustomerId())).thenReturn(ofNullable(customer1));

        boolean result = customerService.changePassword(customer1.getCustomerId(), "newPass1$");

        assertTrue(result);
        assertEquals("newPass1$", customer1.getPassword());

        // test case: non-existing customer
        assertThrows(ResourceNotFoundException.class, () -> customerService.changePassword(99L, "newPass1$"));
    }
}
