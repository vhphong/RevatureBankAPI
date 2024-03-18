package com.pnk.bankapi.service;

import com.pnk.bankapi.core.AccountTypeEnum;
import com.pnk.bankapi.exception.ResourceNotFoundException;
import com.pnk.bankapi.model.Account;
import com.pnk.bankapi.model.Customer;
import com.pnk.bankapi.repository.AccountRepository;
import com.pnk.bankapi.repository.CustomerRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class AccountServiceImplTest {

    @Mock
    private AccountService mockAccountService;

    @Mock
    private AccountRepository mockAccountRepository;

    @Mock
    private CustomerService mockCustomerService;

    @Mock
    private CustomerRepository mockCustomerRepository;

    @InjectMocks
    private AccountServiceImpl injectMockAccountServiceImpl;    // used InjectMocks when a testing class using other class

    @Mock
    AutoCloseable autoCloseable;

    Account account1, account2;

    Customer customer1, customer2;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mock(Account.class);
        mock(AccountRepository.class);
        mock(Customer.class);
        mock(CustomerRepository.class);

        autoCloseable = MockitoAnnotations.openMocks(this);
        mockAccountService = new AccountServiceImpl(mockAccountRepository);
        mockCustomerService = new CustomerServiceImpl(mockCustomerRepository);
        customer1 = new Customer(68L, "John", "john@revbank.com", "pw1");
        customer2 = new Customer(99L, "Jane", "jane@revbank.com", "pw2");
        account1 = new Account(1L, BigDecimal.TEN, customer1);
        account2 = new Account(2L, BigDecimal.valueOf(12.34), AccountTypeEnum.ACCOUNT_TYPE_DEBIT, 2);
    }


    @After
    public void tearDown() throws Exception {
        customer1 = null;
        customer2 = null;
        account1 = null;
        account2 = null;
        autoCloseable.close();
    }


    @Test
    public void testContextLoads() {
        assertThat(mockAccountService).isNotNull();
        assertThat(mockAccountRepository).isNotNull();
        assertThat(mockCustomerService).isNotNull();
        assertThat(mockCustomerRepository).isNotNull();
    }


    @Test
    public void testInsertAccount() {
        // testInsertAccount_success
        when(mockCustomerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.ofNullable(customer1));
        account1.setCustomer(customer1);
        when(mockAccountRepository.save(account1)).thenReturn(account1);

        Assert.assertEquals(account1, injectMockAccountServiceImpl.insertAccount(account1));
    }


    @Test
    public void testListAllAccounts() {
        when(mockAccountRepository.findAll()).thenReturn(List.of(account1));

        Assert.assertEquals(account1, mockAccountService.listAllAccounts().get(0));
        Assert.assertEquals(account1.getAccountId(), mockAccountService.listAllAccounts().get(0).getAccountId());
        Assert.assertEquals(account1.getBalance(), mockAccountService.listAllAccounts().get(0).getBalance());
        Assert.assertEquals(account1.getType(), mockAccountService.listAllAccounts().get(0).getType());
        Assert.assertEquals(account1.getCustomer(), mockAccountService.listAllAccounts().get(0).getCustomer());
    }


    @Test
    public void testListAllAccountsBalanceGreaterThanOrEqual() {
        BigDecimal threshold = BigDecimal.valueOf(100.00);
        when(mockAccountRepository.findByBalanceGreaterThanEqual(threshold))
                .thenReturn(List.of(account1));

        List<Account> accountList = mockAccountService.listAllAccountsBalanceGreaterThanOrEqual(threshold);

        assertEquals(1, accountList.size());
        assertEquals(account1, accountList.get(0));
    }


    @Test
    public void testListAllAccountsBalanceLessThanOrEqual() {
        BigDecimal threshold = BigDecimal.valueOf(100.00);
        when(mockAccountRepository.findByBalanceLessThanEqual(threshold))
                .thenReturn(List.of(account1));

        List<Account> accountList = mockAccountService.listAllAccountsBalanceLessThanOrEqual(threshold);

        assertEquals(1, accountList.size());
        assertEquals(account1, accountList.get(0));
    }


    @Test
    public void testListAllAccountsBalanceInRange() {
        BigDecimal lowerThreshold = BigDecimal.valueOf(100.00);
        BigDecimal upperThreshold = BigDecimal.valueOf(200.00);
        when(mockAccountRepository.findByBalanceBetween(lowerThreshold, upperThreshold))
                .thenReturn(List.of(account1));

        List<Account> accountList = mockAccountService.listAllAccountsBalanceInRange(lowerThreshold, upperThreshold);

        assertEquals(1, accountList.size());
        assertEquals(account1, accountList.get(0));
    }


    @Test
    public void testListAllAccountsByType() {
        String acctType = AccountTypeEnum.ACCOUNT_TYPE_DEBIT;
        when(mockAccountRepository.findByType(acctType)).thenReturn(List.of(account1));

        Assert.assertEquals(account1, mockAccountService.listAllAccountsByType(acctType).get(0));
        verify(mockAccountRepository, times(1)).findByType(acctType);
    }


    @Test
    public void testListAllAccountsByOwnerId() {
        // test case: when customer is existing
        customer1.setAccountList(List.of(account1));
        when(mockCustomerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.ofNullable(customer1));

        List<Account> accountList = injectMockAccountServiceImpl.listAllAccountsByOwnerId(customer1.getCustomerId());

        assertEquals(1, accountList.size());
        assertEquals(account1, accountList.get(0));

        // test case: when customer is not existing
        when(mockCustomerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> injectMockAccountServiceImpl.listAllAccountsByOwnerId(99L));
    }


    @Test
    public void testListAllAccountsByCustomerName() {
        // Given
        String testCustomerName = "John";

        customer1.setAccountList(List.of(account1, account2));
        when(mockCustomerRepository.findByNameContaining(testCustomerName)).thenReturn(Collections.singletonList(customer1));

        // When
        List<Account> accounts = injectMockAccountServiceImpl.listAllAccountsByCustomerName(testCustomerName);

        // Then
        assertEquals(2, accounts.size());
        assertTrue(accounts.contains(account1));
        assertTrue(accounts.contains(account2));
        verify(mockCustomerRepository, times(1)).findByNameContaining(testCustomerName);
    }


    @Test
    public void testListAllAccountsByCustomerEmail() {
        // Given
        String testEmail = "test@example.com";

        customer1.setAccountList(List.of(account1, account2));
        when(mockCustomerRepository.findByEmail(testEmail)).thenReturn(Collections.singletonList(customer1));

        // When
        List<Account> accounts = injectMockAccountServiceImpl.listAllAccountsByCustomerEmail(testEmail);

        // Then
        assertEquals(2, accounts.size());
        assertTrue(accounts.contains(account1));
        assertTrue(accounts.contains(account2));
    }


    @Test
    public void testListAllAccountsByAccountActiveStatus() {
        // test case: whenAccountExists_shouldReturnAccounts
        int acctStatus = 1;
        when(mockAccountRepository.findByActiveStatus(acctStatus)).thenReturn(List.of(account1));

        assertEquals(1, mockAccountService.listAllAccountsByAccountActiveStatus(acctStatus).size());

        // test case: whenAccountDoesNotExist_shouldThrowResourceNotFoundException
        int acctStatus_notExisting = 9;
        when(mockAccountRepository.findByActiveStatus(acctStatus_notExisting)).thenReturn(List.of());
        assertThrows(ResourceNotFoundException.class, () -> mockAccountService.listAllAccountsByAccountActiveStatus(acctStatus_notExisting));
    }


    @Test
    public void testListAccountByAccountId() {
        // test case: whenAccountExists_shouldReturnAccount
        Long acctId = 1L;
        when(mockAccountRepository.findById(acctId)).thenReturn(Optional.ofNullable(account1));
        Assert.assertEquals(account1, mockAccountService.listAccountByAccountId(acctId).get(0));

        // test case: whenAccountDoesNotExist_shouldThrowResourceNotFoundException
        Long acctId_notExisting = 99L;
        when(mockAccountRepository.findById(acctId_notExisting)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> mockAccountService.listAccountByAccountId(acctId_notExisting));
    }


    @Test
    public void testModifyAccount() throws IllegalAccessException {
        // whenAccountExists_shouldReturnAccount
        Long acctId = 1L;
        Account newAccount = new Account(1L, BigDecimal.valueOf(23.45), AccountTypeEnum.ACCOUNT_TYPE_CREDIT, 3, customer1);

        when(mockAccountRepository.findById(acctId)).thenReturn(Optional.ofNullable(account1));
        when(mockAccountRepository.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);

        account1.setBalance(newAccount.getBalance());
        account1.setType(newAccount.getType());
        account1.setActiveStatus(newAccount.getActiveStatus());

        Account modifiedAccount = mockAccountService.modifyAccount(newAccount, acctId);

        assertEquals(newAccount.getBalance(), modifiedAccount.getBalance());
        assertEquals(newAccount.getType(), modifiedAccount.getType());
        assertEquals(newAccount.getActiveStatus(), modifiedAccount.getActiveStatus());

        // whenAccountDoesNotExist_shouldThrowResourceNotFoundException
        Long acctId_notExisting = 99L;
        when(mockAccountRepository.findById(acctId_notExisting)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> mockAccountService.modifyAccount(newAccount, acctId_notExisting));
        verify(mockAccountRepository, times(1)).findById(acctId);
        verify(mockAccountRepository, times(1)).findById(acctId_notExisting);

        // testModifyAccount_NullNewAccount
        assertThrows(IllegalArgumentException.class, () -> mockAccountService.modifyAccount(null, acctId));
    }


    @Test
    public void testSetAccountWithStatus() {
        // test case: whenAccountExists_shouldReturnAccount
        Long acctId = 1L;
        when(mockAccountRepository.findById(acctId)).thenReturn(Optional.ofNullable(account1));
        when(mockAccountRepository.save(account1)).thenReturn(account1);
        mockAccountService.setAccountWithStatus(acctId, 0);
        assertEquals(0, account1.getActiveStatus());
        verify(mockAccountRepository, times(1)).save(account1);

        // test case: whenAccountDoesNotExist_shouldThrowResourceNotFoundException
        Long acctId_notExisting = 99L;
        when(mockAccountRepository.findById(acctId_notExisting)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> mockAccountService.setAccountWithStatus(acctId_notExisting, 0));
    }


    @Test
    public void testRemoveAccount() {
        // testDeleteAccountSuccess
        Long acctId = 1L;
        when(mockAccountRepository.findById(acctId)).thenReturn(Optional.of(account1));
        when(mockAccountRepository.existsById(acctId)).thenReturn(true);
        mockAccountService.removeAccount(acctId);
        boolean resultSuccess = mockAccountService.removeAccount(acctId);
        assertTrue(resultSuccess);

        // testDeleteAccountFailureCatchException
        Long accountIdInput = 99L;
        when(mockAccountRepository.existsById(accountIdInput)).thenReturn(true);
        doThrow(new RuntimeException("Some exception")).when(mockAccountRepository).deleteById(accountIdInput);
        boolean resultException = mockAccountService.removeAccount(accountIdInput);

        assertFalse(resultException);

        // test case: whenAccountDoesNotExist_shouldThrowExceptionMessage
        Long acctId_notExisting = 99L;
        when(mockAccountRepository.findById(acctId_notExisting)).thenReturn(Optional.empty());
        when(mockAccountRepository.existsById(acctId_notExisting)).thenReturn(false);
        doThrow(new RuntimeException("Failed to delete account")).when(mockAccountRepository).deleteById(acctId_notExisting);

        assertThrows(ResourceNotFoundException.class, () -> mockAccountService.removeAccount(acctId_notExisting));

        // testDeleteAccountFailureDueToInvalidId
        Long acctId_negative = -99L;

        assertThrows(ResourceNotFoundException.class, () -> mockAccountService.removeAccount(acctId_negative));

        verify(mockAccountRepository, times(2)).deleteById(acctId);
    }


    @Test
    public void testVerifyOwnershipOfAccount() {
        // testVerifyOwnershipOfAccount_Success
        Long acctId = 1L;
        account1.setCustomer(customer1);
        when(mockAccountRepository.findById(acctId)).thenReturn(Optional.ofNullable(account1));

        Assert.assertEquals(account1, mockAccountService.verifyOwnershipOfAccount(customer1.getCustomerId(), acctId));

        // testVerifyOwnershipOfAccount_ResourceNotFoundException
        Long accId_notFound = 99L;
        when(mockAccountRepository.findById(accId_notFound)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> mockAccountService.verifyOwnershipOfAccount(customer1.getCustomerId(), accId_notFound));

        // testVerifyOwnershipOfAccount_NotOwned_ReturnNull
        assertNull(mockAccountService.verifyOwnershipOfAccount(customer2.getCustomerId(), acctId));
    }


    @Test
    public void testValidateTransferAmount() {
        // testValidateTransferAmount_Success
        Long acctId = 1L;
        account1.setBalance(BigDecimal.TEN);
        BigDecimal amount = BigDecimal.ONE;
        when(mockAccountRepository.findById(acctId)).thenReturn(Optional.ofNullable(account1));
        assertTrue(mockAccountService.validateTransferAmount(acctId, amount));

        // testValidateTransferAmount_AccountNotFound
        Long accId_notFound = 99L;
        when(mockAccountRepository.findById(accId_notFound)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> mockAccountService.validateTransferAmount(accId_notFound, amount));

        // testValidateTransferAmount_Balance<Amount
        BigDecimal amountGreater = BigDecimal.valueOf(100.00);
        assertFalse(mockAccountService.validateTransferAmount(acctId, amountGreater));
    }
}
