package com.pnk.bankapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pnk.bankapi.core.AccountTypeEnum;
import com.pnk.bankapi.exception.BadRequestException;
import com.pnk.bankapi.exception.ResourceNotFoundException;
import com.pnk.bankapi.model.Account;
import com.pnk.bankapi.model.Customer;
import com.pnk.bankapi.repository.AccountRepository;
import com.pnk.bankapi.service.AccountService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(value = AccountController.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountControllerTest {

    @InjectMocks
    private AccountController injectMockAccountController;

    @MockBean
    private AccountService mockAccountService;

    @MockBean
    private AccountRepository mockAccountRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Value("${server.port}")
    public int serverPort;
    Account account1, account2;
    Customer customer1, customer2;
    String requestMapping = "/RevBankAPI/v2/accounts";

    @Before
    public void setUp() throws Exception {
        account1 = new Account(1L, BigDecimal.valueOf(12.34), customer1);
        account2 = new Account(2L, BigDecimal.TEN, AccountTypeEnum.ACCOUNT_TYPE_DEBIT, 6);

        customer1 = new Customer(11L, "John", List.of(account1));
        customer2 = new Customer(22L, "Jane", "jane@revbank.com", "jane12");

        account1.setCustomer(customer1);
        account2.setCustomer(customer2);
    }

    @After
    public void tearDown() throws Exception {
        injectMockAccountController = null;
        account1 = null;
        account2 = null;
        customer1 = null;
        customer2 = null;
    }


    @Test
    public void testWelcomeAccounts() throws Exception {
        mockMvc.perform(get(requestMapping + "/welcome"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from the endpoint accounts/welcome. The application is listening on PORT: " + serverPort));
    }


    @Test
    public void testSaveAccount() throws Exception {
        // Valid data then Success
        when(mockAccountService.insertAccount(any(Account.class))).thenReturn(account1);

        // Perform the POST request to the controller
        mockMvc.perform(post(requestMapping + "/create").contentType(MediaType.APPLICATION_JSON).content("""
                        {
                            "balance": 43242.43,
                            "type": "credit",
                            "activeStatus": 6,
                            "customer": {
                                "customerId": 9561
                            }
                        }"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").value(1))
                .andExpect(jsonPath("$.balance").value(12.34))
                .andExpect(jsonPath("$.customer.customerId").value(11));

        verify(mockAccountService, times(1)).insertAccount(any(Account.class));

        // Invalid/Null data then throw BadRequestException
        assertThrows(BadRequestException.class, () -> {
            injectMockAccountController.saveAccount(null);
        });
    }


    @Test
    public void testGetFilteredAccounts() throws Exception {
        // listAccountByAccountId
        Long accountId = 101L;
        when(mockAccountService.listAccountByAccountId(accountId)).thenReturn(List.of(account1));
        mockMvc.perform(get(requestMapping)
                        .param("id", accountId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].balance").value(account1.getBalance()))
                .andExpect(jsonPath("$[0].type").value(account1.getType()));
        verify(mockAccountService).listAccountByAccountId(accountId);

        // listAllAccounts
        when(mockAccountService.listAllAccounts()).thenReturn(List.of(account2));
        mockMvc.perform(get(requestMapping)
                        .param("all", ""))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].balance").value(account2.getBalance()))
                .andExpect(jsonPath("$[0].type").value(account2.getType()));
        verify(mockAccountService).listAllAccounts();

        // listAllAccountsByAccountActiveStatus
        int accountStatus = 1;
        when(mockAccountService.listAllAccountsByAccountActiveStatus(1)).thenReturn(List.of(account2));
        mockMvc.perform(get(requestMapping)
                        .param("activeStatus", "" + accountStatus))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance").value(account2.getBalance()))
                .andExpect(jsonPath("$[0].type").value(account2.getType()))
                .andExpect(jsonPath("$[0].activeStatus").value(account2.getActiveStatus()))
        ;
        verify(mockAccountService).listAllAccountsByAccountActiveStatus(accountStatus);

        // listAllAccountsByOwnerId
        Long customerId = 1L;
        when(mockAccountService.listAllAccountsByOwnerId(1L)).thenReturn(List.of(account2));
        mockMvc.perform(get(requestMapping)
                        .param("customerId", customerId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance").value(account2.getBalance()))
                .andExpect(jsonPath("$[0].type").value(account2.getType()))
                .andExpect(jsonPath("$[0].activeStatus").value(account2.getActiveStatus()))
                .andExpect(jsonPath("$[0].customer").value(customer2))
        ;
        verify(mockAccountService).listAllAccountsByOwnerId(customerId);

        // listAllAccountsByCustomerEmail
        String customerEmail = "johndoe@example.com";
        when(mockAccountService.listAllAccountsByCustomerEmail(customerEmail)).thenReturn(List.of(account2));
        mockMvc.perform(get(requestMapping)
                        .param("customerEmail", customerEmail))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance").value(account2.getBalance()))
                .andExpect(jsonPath("$[0].type").value(account2.getType()))
                .andExpect(jsonPath("$[0].activeStatus").value(account2.getActiveStatus()))
                .andExpect(jsonPath("$[0].customer.email").value(customer2.getEmail()))
        ;
        verify(mockAccountService).listAllAccountsByCustomerEmail(customerEmail);

        // listAllAccountsByCustomerName
        String customerName = "John Doe";
        when(mockAccountService.listAllAccountsByCustomerName(customerName)).thenReturn(List.of(account2));
        mockMvc.perform(get(requestMapping)
                        .param("customerName", customerName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance").value(account2.getBalance()))
                .andExpect(jsonPath("$[0].type").value(account2.getType()))
                .andExpect(jsonPath("$[0].activeStatus").value(account2.getActiveStatus()))
                .andExpect(jsonPath("$[0].customer").value(customer2))
                .andExpect(jsonPath("$[0].customer.name").value(customer2.getName()))
        ;
        verify(mockAccountService).listAllAccountsByCustomerName(customerName);

        // listAllAccountsByType
        String acctType = "SAVING";
        when(mockAccountService.listAllAccountsByType(acctType)).thenReturn(List.of(account2));
        mockMvc.perform(get(requestMapping)
                        .param("type", acctType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance").value(account2.getBalance()))
                .andExpect(jsonPath("$[0].type").value(account2.getType()))
                .andExpect(jsonPath("$[0].activeStatus").value(account2.getActiveStatus()))
                .andExpect(jsonPath("$[0].customer").value(customer2))
                .andExpect(jsonPath("$[0].customer.name").value(customer2.getName()))
        ;
        verify(mockAccountService).listAllAccountsByType(acctType);

        // listAllAccountsBalanceLessThanOrEqual
        BigDecimal maxValue = BigDecimal.TEN;
        when(mockAccountService.listAllAccountsBalanceLessThanOrEqual(maxValue)).thenReturn(List.of(account2));
        mockMvc.perform(get(requestMapping)
                        .param("maxBalance", maxValue.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance").value(account2.getBalance()))
                .andExpect(jsonPath("$[0].type").value(account2.getType()))
                .andExpect(jsonPath("$[0].activeStatus").value(account2.getActiveStatus()))
                .andExpect(jsonPath("$[0].customer").value(customer2))
                .andExpect(jsonPath("$[0].customer.name").value(customer2.getName()))
        ;
        verify(mockAccountService).listAllAccountsBalanceLessThanOrEqual(maxValue);

        // listAllAccountsBalanceGreaterThanOrEqual
        BigDecimal minValue = BigDecimal.ZERO;
        when(mockAccountService.listAllAccountsBalanceGreaterThanOrEqual(minValue)).thenReturn(List.of(account2));
        mockMvc.perform(get(requestMapping)
                        .param("minBalance", minValue.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance").value(account2.getBalance()))
                .andExpect(jsonPath("$[0].type").value(account2.getType()))
                .andExpect(jsonPath("$[0].activeStatus").value(account2.getActiveStatus()))
                .andExpect(jsonPath("$[0].customer").value(customer2))
                .andExpect(jsonPath("$[0].customer.name").value(customer2.getName()))
        ;
        verify(mockAccountService).listAllAccountsBalanceGreaterThanOrEqual(minValue);

        // listAllAccountsBalanceInRange
        when(mockAccountService.listAllAccountsBalanceInRange(minValue, maxValue)).thenReturn(List.of(account2));
        mockMvc.perform(get(requestMapping)
                        .param("minBalance", minValue.toString())
                        .param("maxBalance", maxValue.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance").value(account2.getBalance()))
                .andExpect(jsonPath("$[0].type").value(account2.getType()))
                .andExpect(jsonPath("$[0].activeStatus").value(account2.getActiveStatus()))
                .andExpect(jsonPath("$[0].customer").value(customer2))
                .andExpect(jsonPath("$[0].customer.name").value(customer2.getName()))
        ;
        verify(mockAccountService).listAllAccountsBalanceInRange(minValue, maxValue);

        // listAllAccountsBalanceInRange with null range
        when(mockAccountService.listAllAccountsBalanceInRange(null, null))
                .thenReturn(Collections.emptyList());
        mockMvc.perform(get(requestMapping)
                        .param("minBalance", "")
                        .param("maxBalance", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(Collections.emptyList().toString()));

        // throw ResourceNotFoundException as notFound
        when(mockAccountService.listAllAccounts())
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        mockMvc.perform(get(requestMapping)
                        .param("all", ""))
                .andExpect(status().isNotFound());


    }


    @Test
    public void testUpdateAccount() throws Exception {
        Account newAccount = new Account();
        newAccount.setAccountId(1L);
        newAccount.setBalance(BigDecimal.ONE);
        newAccount.setOpeningDate(LocalDateTime.now());
        newAccount.setType("SAVING");
        newAccount.setActiveStatus(1);

        when(mockAccountService.modifyAccount(newAccount, 1L)).thenReturn(newAccount);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String accountJson = objectMapper.writeValueAsString(newAccount);

        mockMvc.perform(put(requestMapping + "?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(1.00))
                .andExpect(jsonPath("$.type").value("SAVING"))
                .andExpect(jsonPath("$.activeStatus").value(1));

        verify(mockAccountService, times(1)).modifyAccount(newAccount, 1L);
    }


    @Test
    public void testSetAccountActiveStatus() throws Exception {
        // testSetAccountActiveStatus_activate
        account2.setActiveStatus(0);
        when(mockAccountRepository.findById(2L)).thenReturn(Optional.ofNullable(account2));

        Account modifiedAccount = new Account();
        modifiedAccount.setActiveStatus(1);

        when(mockAccountService.setAccountWithStatus(2L, 1)).thenReturn(modifiedAccount);

        mockMvc.perform(patch(requestMapping + "/status?activate=2")).andExpect(status().isOk()).andExpect(jsonPath("$.activeStatus").value(1));

        // testSetAccountActiveStatus_deactivate
        account2.setActiveStatus(1);
        when(mockAccountRepository.findById(2L)).thenReturn(Optional.ofNullable(account2));

        modifiedAccount.setActiveStatus(0);

        when(mockAccountService.setAccountWithStatus(2L, 0)).thenReturn(modifiedAccount);

        mockMvc.perform(patch(requestMapping + "/status?deactivate=2")).andExpect(status().isOk()).andExpect(jsonPath("$.activeStatus").value(0));

        // throw BadRequestException
        mockMvc.perform(patch(requestMapping + "/status?activate=2&deactivate=5")).andExpect(status().isBadRequest());
        mockMvc.perform(patch(requestMapping + "/status?")).andExpect(status().isBadRequest());
    }


    @Test
    public void testSetAccountActiveStatus_deactivate() throws Exception {
        account2.setActiveStatus(1);
        when(mockAccountRepository.findById(2L)).thenReturn(Optional.ofNullable(account2));

        Account modifiedAccount = new Account();
        modifiedAccount.setActiveStatus(0);

        when(mockAccountService.setAccountWithStatus(2L, 0)).thenReturn(modifiedAccount);

        mockMvc.perform(patch(requestMapping + "/status?deactivate=2")).andExpect(status().isOk()).andExpect(jsonPath("$.activeStatus").value(0));
    }


    @Test
    public void testDeleteAccount() throws Exception {
        // testTransferBetweenAccountsOfSameOwner_success
        when(mockAccountService.removeAccount(1L)).thenReturn(true);

        mockMvc.perform(delete(requestMapping + "/id/1")).andExpect(status().isGone());

        // testTransferBetweenAccountsOfSameOwner_notFound
        when(mockAccountService.removeAccount(99L)).thenReturn(false);

        mockMvc.perform(delete(requestMapping + "/id/99")).andExpect(status().isNotFound());
    }
}