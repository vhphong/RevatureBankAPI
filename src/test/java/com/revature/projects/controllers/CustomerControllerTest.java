package com.revature.projects.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.models.Customer;
import com.revature.projects.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(value = CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerController customerController;

    @Autowired
    private MockMvc mockMvc;

    // test of CustomerController's welcomeCustomer
    // @Test
    // public void welcomeCustomerTest() throws Exception {
    // when(customerService.welcomeCustomer()).thenReturn("Welcome!");
    // when(customerController.welcomeCustomer()).thenReturn(new
    // ResponseEntity<String>("Welcome, Phong!", HttpStatus.OK));
    // MockHttpServletRequestBuilder requestBuilder =
    // MockMvcRequestBuilders.get("/RevBankAPI/v2/welcome");

    // ResultActions perform = mockMvc.perform(requestBuilder);
    // MvcResult mvcResult = perform.andReturn();
    // MockHttpServletResponse response = mvcResult.getResponse();

    // // assertEquals(200, response.getStatus());
    // assertEquals(HttpStatus.OK.value(), response.getStatus());
    // }

    // test of CustomerController's saveCustomer
    @Test
    public void saveCustomerTest() throws Exception {
        when(customerService.insertCustomer(ArgumentMatchers.any())).thenReturn(new Customer());

        Customer newTestCustomer = new Customer("test name", "testemail@email.com", "testpassword");
        ObjectMapper objectMapper = new ObjectMapper();
        String customerJson = objectMapper.writeValueAsString(newTestCustomer);

        RequestBuilder requestBuilder = post("/RevBankAPI/v2/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson);

        ResultActions perform = mockMvc.perform(requestBuilder);
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        // assertEquals(200, response.getStatus());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    // test of CustomerController's getAllCustomers
    @Test
    public void getAllCustomersTest() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerName("testname");
        customer.setCustomerEmail("testemail@email.com");
        customer.setCustomerPassword("123");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonReq = objectMapper.writeValueAsString(customer);

        RequestBuilder requestBuilder = get("/RevBankAPI/v2/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonReq);

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());
    }
}
