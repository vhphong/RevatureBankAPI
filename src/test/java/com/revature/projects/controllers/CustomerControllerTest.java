package com.revature.projects.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.models.Customer;
import com.revature.projects.repositories.CustomerRepository;
import com.revature.projects.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(value = CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerController customerController;

    @MockBean
    private CustomerRepository customerRepository;

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

        RequestBuilder requestBuilder = post("/RevBankAPI/v2/customers/create").contentType(MediaType.APPLICATION_JSON).content(customerJson);

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

        RequestBuilder requestBuilder = get("/RevBankAPI/v2/customers/all").contentType(MediaType.APPLICATION_JSON).content(jsonReq);

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());
    }


    // test of CustomerController's getCustomerById
    @Test
    void getCustomerByIdTest() throws Exception {
        // when
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        String dateInString = "7-Jun-2013";
        Date date = formatter.parse(dateInString);
        // when
        Customer addedCustomer = new Customer(1L, "phong", "phong@email.com", date, "11", "123 A St", "fakepwd");
        customerService.insertCustomer(addedCustomer);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonReq = objectMapper.writeValueAsString(addedCustomer);

        RequestBuilder requestBuilder = get("/RevBankAPI/v2/customers/id/" + addedCustomer.getCustomerId()).contentType(MediaType.APPLICATION_JSON).content(jsonReq);

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());
    }


    @Test
    void getCustomerByNameTest() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        String dateInString = "7-Jun-2013";
        Date date = formatter.parse(dateInString);
        // when
        Customer addedCustomer = new Customer(1L, "phong", "phong@email.com", date, "11", "123 A St", "fakepwd");
        customerService.insertCustomer(addedCustomer);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonReq = objectMapper.writeValueAsString(addedCustomer);

        RequestBuilder requestBuilder = get("/RevBankAPI/v2/customers/name/" + addedCustomer.getCustomerName()).contentType(MediaType.APPLICATION_JSON).content(jsonReq);

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());
    }


    @Test
    void getCustomerByEmailTest() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        String dateInString = "7-Jun-2013";
        Date date = formatter.parse(dateInString);
        // when
        Customer addedCustomer = new Customer(1L, "phong", "phong@email.com", date, "11", "123 A St", "fakepwd");
        customerService.insertCustomer(addedCustomer);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonReq = objectMapper.writeValueAsString(addedCustomer);

        RequestBuilder requestBuilder = get("/RevBankAPI/v2/customers/email/" + addedCustomer.getCustomerEmail()).contentType(MediaType.APPLICATION_JSON).content(jsonReq);

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());
    }


    @Test
    void getCustomerByNameAndEmailTest() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        String dateInString = "7-Jun-2013";
        Date date = formatter.parse(dateInString);
        // when
        Customer addedCustomer = new Customer(1L, "phong", "phong@email.com", date, "11", "123 A St", "fakepwd");
        customerService.insertCustomer(addedCustomer);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonReq = objectMapper.writeValueAsString(addedCustomer);

        RequestBuilder requestBuilder = get("/RevBankAPI/v2/customers/name/" + addedCustomer.getCustomerName() + "/email/" + addedCustomer.getCustomerEmail()).contentType(MediaType.APPLICATION_JSON).content(jsonReq);

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());
    }


    @Test
    void updateCustomerTest() throws Exception {
    }


    @Test
    void deleteCustomerTest() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        String dateInString = "7-Jun-2013";
        Date date = formatter.parse(dateInString);
        // when
        Customer addedCustomer = new Customer(1L, "phong", "phong@email.com", date, "11", "123 A St", "fakepwd");
        customerService.insertCustomer(addedCustomer);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonReq = objectMapper.writeValueAsString(addedCustomer);

        RequestBuilder requestBuilder = delete("/RevBankAPI/v2/customers/id/" + addedCustomer.getCustomerId()).contentType(MediaType.APPLICATION_JSON).content(jsonReq);

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());
    }


    @Test
    void checkEmailIsTaken() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        String dateInString = "7-Jun-2013";
        Date date = formatter.parse(dateInString);
        // when
        Customer addedCustomer = new Customer(1L, "phong", "phong@email.com", date, "11", "123 A St", "fakepwd");
        customerService.insertCustomer(addedCustomer);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonReq = objectMapper.writeValueAsString(addedCustomer);

        RequestBuilder requestBuilder = get("/RevBankAPI/v2/customers/existing_email/" + addedCustomer.getCustomerEmail()).contentType(MediaType.APPLICATION_JSON).content(jsonReq);

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());
    }
}