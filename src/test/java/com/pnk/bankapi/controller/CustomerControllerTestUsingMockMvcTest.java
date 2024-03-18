package com.pnk.bankapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnk.bankapi.dto.CustomerDTO;
import com.pnk.bankapi.exception.BadRequestException;
import com.pnk.bankapi.exception.ResourceNotFoundException;
import com.pnk.bankapi.model.Account;
import com.pnk.bankapi.model.Customer;
import com.pnk.bankapi.repository.CustomerRepository;
import com.pnk.bankapi.service.CustomerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTestUsingMockMvcTest {

    private static final String ENDPOINT_PATH = "/RevBankAPI/v2/customers";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService mockCustomerService;

    @MockBean
    private CustomerRepository mockCustomerRepository;

    @Autowired
    private ObjectMapper mockObjectMapper;

    @Value("${server.port}")
    public int serverPort;

    Customer customer1, customer2;

    CustomerDTO targetCustomerDTO;

    @Before
    public void setUp() throws Exception {
        customer1 = new Customer(11L, "John", List.of(new Account()));
        customer2 = new Customer(22L, "Jane", "jane@revbank.com", "jane12");

        targetCustomerDTO = new CustomerDTO();

        targetCustomerDTO.setName("New Name");
        targetCustomerDTO.setEmail("newemail@email.com");
        targetCustomerDTO.setDob(LocalDate.of(2001, 11, 18));
        targetCustomerDTO.setPhone("6625550144");
        targetCustomerDTO.setAddress("123 Main St");
        targetCustomerDTO.setPassword("newPassword");
        targetCustomerDTO.setCreatedDate(LocalDateTime.of(2019, 12, 19, 14, 46));
        targetCustomerDTO.setLastUpdatedDate(LocalDateTime.of(2020, 4, 18, 10, 0, 0));
        targetCustomerDTO.setAccountList(new ArrayList<>());
    }

    @After
    public void tearDown() throws Exception {
        customer1 = null;
        customer2 = null;
    }


    @Test
    public void testContextLoads() {
        assertThat(mockCustomerService).isNotNull();
    }


    @Test
    public void testWelcomeCustomers() throws Exception {
        String greetingStr = "Welcome to the endpoint customers/welcome. The application is listening on PORT: " + serverPort;

        when(mockCustomerService.greet()).thenReturn(greetingStr);

        mockMvc.perform(get(ENDPOINT_PATH + "/welcome"))
                .andExpect(status().isOk())
                .andExpect(content().string(greetingStr));
    }


    @Test
    public void testSaveCustomerShouldReturn400BadRequest() throws Exception {
        String emptyRequestBody = "";

        mockMvc.perform(post(ENDPOINT_PATH + "/create")
                        .contentType("application/json")
                        .content(emptyRequestBody))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testSaveCustomerShouldReturn201Created() throws Exception {
        String requestBody = mockObjectMapper.writeValueAsString(customer2);

        when(mockCustomerService.insertCustomer(customer2)).thenReturn(customer2);

        mockMvc.perform(post(ENDPOINT_PATH + "/create")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jane"))
                .andExpect(jsonPath("$.email").value("jane@revbank.com"))
                .andExpect(jsonPath("$.password", is("jane12")))
                .andExpect(header().string("Location", is("/customers/22"))) // Check Location header
                .andDo(print());

        verify(mockCustomerService, times(1)).insertCustomer(customer2);
    }


    @Test
    public void testGetAllCustomersShouldReturn200Ok() throws Exception {
        when(mockCustomerService.listAllCustomers()).thenReturn(List.of(customer1));

        mockMvc.perform(get(ENDPOINT_PATH).param("all", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(mockObjectMapper.writeValueAsString(List.of(customer1))))
                .andDo(print());

        verify(mockCustomerService, times(1)).listAllCustomers();
    }


    @Test
    public void testGetAllCustomersShouldReturn404NotFound() throws Exception {
        when(mockCustomerService.listAllCustomers()).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(ENDPOINT_PATH).param("all", ""))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(mockCustomerService, times(1)).listAllCustomers();
    }


    @Test
    public void testListCustomerByIdShouldReturn200Ok() throws Exception {
        when(mockCustomerService.listCustomerById(22L)).thenReturn(List.of(customer2));

        mockMvc.perform(get(ENDPOINT_PATH).param("id", "22"))
                .andExpect(status().isOk())
                .andExpect(content().string(mockObjectMapper.writeValueAsString(List.of(customer2))))
                .andDo(print());

        verify(mockCustomerService, times(1)).listCustomerById(22L);
    }


    @Test
    public void testListCustomerByIdShouldReturn404NotFound() throws Exception {
        when(mockCustomerService.listCustomerById(0L)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(ENDPOINT_PATH).param("id", "0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(print());

        verify(mockCustomerService, times(1)).listCustomerById(0L);
    }


    @Test
    public void testListCustomerByNameAndEmailShouldReturn200Ok() throws Exception {
        when(mockCustomerService.listCustomerByNameAndEmail(customer2.getName(), customer2.getEmail()))
                .thenReturn(List.of(customer2));

        mockMvc.perform(get(ENDPOINT_PATH).param("name", "Jane").param("email", "jane@revbank.com"))
                .andExpect(status().isOk())
                .andExpect(content().string(mockObjectMapper.writeValueAsString(List.of(customer2))))
                .andDo(print());

        verify(mockCustomerService, times(1)).listCustomerByNameAndEmail("Jane", "jane@revbank.com");
    }


    @Test
    public void testListCustomerByNameAndEmailShouldReturn404NotFound() throws Exception {
        when(mockCustomerService.listCustomerByNameAndEmail(customer2.getName(), customer2.getEmail()))
                .thenThrow(new ResourceNotFoundException("Resource not found"));

        mockMvc.perform(get(ENDPOINT_PATH).param("name", "Jane").param("email", "jane@revbank.com"))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(mockCustomerService, times(1)).listCustomerByNameAndEmail("Jane", "jane@revbank.com");
    }


    @Test
    public void testListCustomerByNameShouldReturn200Ok() throws Exception {
        when(mockCustomerService.listAllCustomersByName(customer1.getName())).thenReturn(List.of(customer1));

        mockMvc.perform(get(ENDPOINT_PATH).param("name", "John"))
                .andExpect(status().isOk())
                .andExpect(content().string(mockObjectMapper.writeValueAsString(List.of(customer1))))
                .andDo(print());

        verify(mockCustomerService, times(1)).listAllCustomersByName("John");
    }


    @Test
    public void testListCustomerByNameShouldReturn404NotFound() throws Exception {
        when(mockCustomerService.listAllCustomersByName(customer1.getName())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(ENDPOINT_PATH).param("name", "John"))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(mockCustomerService, times(1)).listAllCustomersByName("John");
    }


    @Test
    public void testListCustomerByEmailShouldReturn200Ok() throws Exception {
        when(mockCustomerService.listAllCustomersByEmail(customer2.getEmail())).thenReturn(List.of(customer2));

        mockMvc.perform(get(ENDPOINT_PATH).param("email", "jane@revbank.com"))
                .andExpect(status().isOk())
                .andExpect(content().string(mockObjectMapper.writeValueAsString(List.of(customer2))))
                .andDo(print());

        verify(mockCustomerService, times(1)).listAllCustomersByEmail("jane@revbank.com");
    }


    @Test
    public void testListCustomerByEmailShouldReturn404NotFound() throws Exception {
        when(mockCustomerService.listAllCustomersByEmail(customer2.getEmail()))
                .thenThrow(new ResourceNotFoundException("Resource not found"));

        mockMvc.perform(get(ENDPOINT_PATH).param("email", "jane@revbank.com"))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(mockCustomerService, times(1)).listAllCustomersByEmail("jane@revbank.com");
    }


    @Test
    public void testUpdateCustomerShouldReturn200Ok() throws Exception {
        String requestBody = mockObjectMapper.writeValueAsString(targetCustomerDTO);

        Customer modifiedCustomer = new Customer(
                1L,
                "New Name",
                "newemail@email.com",
                LocalDate.of(2001, 11, 18),
                "6625550144",
                "123 Main St",
                "newPassword",
                LocalDateTime.of(2019, 12, 19, 14, 46),
                LocalDateTime.of(2020, 4, 18, 10, 0, 0),
                new ArrayList<>()
        );

        when(mockCustomerRepository.findById(1L)).thenReturn(Optional.ofNullable(customer1));
        when(mockCustomerService.modifyCustomer(any(CustomerDTO.class), any(Long.class))).thenReturn(modifiedCustomer);

        // Perform the PATCH request
        mockMvc.perform(patch(ENDPOINT_PATH + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.email").value("newemail@email.com"))
                .andExpect(jsonPath("$.password").value("newPassword"));

        verify(mockCustomerService, times(1)).modifyCustomer(targetCustomerDTO, modifiedCustomer.getCustomerId());
    }


    @Test
    public void testUpdateCustomerShouldReturn400BadRequest() throws Exception {
        String requestBody = mockObjectMapper.writeValueAsString(targetCustomerDTO);

        when(mockCustomerRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());
        when(mockCustomerService.modifyCustomer(any(CustomerDTO.class), any(Long.class)))
                .thenThrow(new BadRequestException("An exception occurred"));

        mockMvc.perform(patch(ENDPOINT_PATH + "/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
