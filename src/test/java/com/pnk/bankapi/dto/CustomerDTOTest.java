package com.pnk.bankapi.dto;

import com.pnk.bankapi.model.Customer;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertSame;


public class CustomerDTOTest {

    @Test
    public void testBuild() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO();

        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setName("Name");
        customer.setEmail("jane.doe@example.org");
        customer.setDob(LocalDate.of(1988, 4, 16));
        customer.setPhone("6625550144");
        customer.setAddress("42 Main St");
        customer.setPassword("password12");
        customer.setCreatedDate(LocalDateTime.of(2020, 4, 16, 22, 4, 2));
        customer.setLastUpdatedDate(LocalDateTime.of(2020, 6, 18, 0, 0));
        customer.setAccountList(new ArrayList<>());

        // Act and Assert
        assertSame(customerDTO, customerDTO.build(customer));
    }
}