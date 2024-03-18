package com.pnk.bankapi.service;

import com.pnk.bankapi.core.TransactionStatusEnum;
import com.pnk.bankapi.core.TransactionType;
import com.pnk.bankapi.exception.ResourceNotFoundException;
import com.pnk.bankapi.exception.UnsupportedMediaType;
import com.pnk.bankapi.model.Account;
import com.pnk.bankapi.model.Customer;
import com.pnk.bankapi.model.Transaction;
import com.pnk.bankapi.repository.AccountRepository;
import com.pnk.bankapi.repository.CustomerRepository;
import com.pnk.bankapi.repository.TransactionRepository;
import com.pnk.bankapi.util.Utils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class TransactionServiceImplTest {

    @Mock
    private TransactionService mockTransactionService = mock(TransactionService.class);

    @Mock
    private TransactionRepository mockTransactionRepository;

    @Mock
    private CustomerService mockCustomerService;

    @Mock
    private CustomerRepository mockCustomerRepository;

    @Mock
    private AccountService mockAccountService;

    @Mock
    private AccountRepository mockAccountRepository;

    @Mock
    AutoCloseable autoCloseable;

    Transaction transaction1, transaction2;
    Customer customer1;
    Account account1, account2;
    Date date1;

    @Before
    public void setUp() throws Exception {
        autoCloseable = MockitoAnnotations.openMocks(this);
        mockTransactionService = new TransactionServiceImpl(mockTransactionRepository);
        mockAccountService = new AccountServiceImpl(mockAccountRepository);

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        UUID transactionUuid1 = Utils.generateUUID();
        String dateInString1 = "03-28-2022";
        date1 = formatter.parse(dateInString1);
        transaction1 = new Transaction(transactionUuid1, Utils.generateRandomStringAZ09(10), 1L, 101L, 2L, 201L, BigDecimal.ONE);
        transaction1.setStatus(TransactionStatusEnum.SCHEDULED);
        customer1 = new Customer();
        account1 = new Account();
        account1.setCustomer(customer1);
    }

    @After
    public void tearDown() throws Exception {
        transaction1 = null;
        transaction2 = null;
        autoCloseable.close();
    }


    @Test
    public void testInsertTransaction() {
        // test case: input is valid type of Transaction
        when(mockTransactionRepository.save(transaction1)).thenReturn(transaction1);

        Assert.assertEquals(transaction1, mockTransactionService.insertTransaction(transaction1));

        // test case: input is invalid/null type of Transaction
        assertThrows(UnsupportedMediaType.class, () -> mockTransactionService.insertTransaction(null));
    }


    @Test
    public void testListTransactionsByTransactionId() {
        // testListTransactionsByTransactionId_null_input
        assertThrows(UnsupportedMediaType.class,
                () -> mockTransactionService.listTransactionsByTransactionId(null));

        // testListTransactionsByTransactionId_not_found
        UUID transactionIdInput_notFound = Utils.generateUUID();
        when(mockTransactionRepository.findById(transactionIdInput_notFound)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> mockTransactionService.listTransactionsByTransactionId(transactionIdInput_notFound));

        // testListTransactionsByTransactionId_success
        UUID transactionIdInput = Utils.generateUUID();
        when(mockTransactionRepository.findById(transactionIdInput)).thenReturn(Optional.ofNullable(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsByTransactionId(transactionIdInput).get(0));
    }


    @Test
    public void testListAllTransactions() {
        assertEquals(mockTransactionRepository.findAll().size(), mockTransactionService.listAllTransactions().size());
    }


    @Test
    public void testListTransactionsByReferenceId() {
        String referenceId = Utils.generateRandomStringAZ09(10);
        when(mockTransactionRepository.findByReferenceId(referenceId)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsByReferenceId(referenceId).get(0));
    }


    @Test
    public void testListTransactionsBySourceOwnerId() {
        when(mockTransactionRepository.findBySourceOwnerId(1L)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsBySourceOwnerId(1L).get(0));
    }


    @Test
    public void testListTransactionsBySourceAccountId() {
        when(mockTransactionRepository.findBySourceAccountId(1L)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsBySourceAccountId(1L).get(0));
    }


    @Test
    public void testListTransactionsByDestinedOwnerId() {
        when(mockTransactionRepository.findByDestinedOwnerId(1L)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsByDestinedOwnerId(1L).get(0));
    }


    @Test
    public void testListTransactionsByDestinedAccountId() {
        when(mockTransactionRepository.findByDestinedAccountId(1L)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsByDestinedAccountId(1L).get(0));
    }


    @Test
    public void testListTransactionsByAmountLessThan() {
        BigDecimal upperThresholdInput = BigDecimal.TEN;
        when(mockTransactionRepository.findByAmountLessThan(upperThresholdInput)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsByAmountLessThan(upperThresholdInput).get(0));
    }


    @Test
    public void testListTransactionsByAmountLessThanEqual() {
        BigDecimal upperThresholdInput = BigDecimal.TEN;
        when(mockTransactionRepository.findByAmountLessThanEqual(upperThresholdInput)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsByAmountLessThanEqual(upperThresholdInput).get(0));
    }


    @Test
    public void testListTransactionsByAmountGreaterThan() {
        BigDecimal lowerThresholdInput = BigDecimal.ZERO;
        when(mockTransactionRepository.findByAmountGreaterThan(lowerThresholdInput)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsByAmountGreaterThan(lowerThresholdInput).get(0));
    }


    @Test
    public void testListTransactionsByAmountGreaterThanEqual() {
        BigDecimal lowerThresholdInput = BigDecimal.ZERO;
        when(mockTransactionRepository.findByAmountGreaterThanEqual(lowerThresholdInput)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsByAmountGreaterThanEqual(lowerThresholdInput).get(0));
    }


    @Test
    public void testListTransactionsByAmountBetween() {
        BigDecimal upperThresholdInput = BigDecimal.TEN;
        BigDecimal lowerThresholdInput = BigDecimal.ZERO;
        when(mockTransactionRepository.findByAmountBetween(lowerThresholdInput, upperThresholdInput))
                .thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsByAmountBetween(lowerThresholdInput, upperThresholdInput).get(0));
    }


    @Test
    public void testListTransactionsByDetailStartingWith() {
        String prefixInput = "sample";
        when(mockTransactionRepository.findByDetailStartingWith(prefixInput)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsByDetailStartingWith(prefixInput).get(0));
    }


    @Test
    public void testListTransactionsByDetailEndingWith() {
        String suffixInput = "sample";
        when(mockTransactionRepository.findByDetailEndingWith(suffixInput)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsByDetailEndingWith(suffixInput).get(0));
    }


    @Test
    public void testListTransactionsByDetailContaining() {
        String infixInput = "sample";
        when(mockTransactionRepository.findByDetailContaining(infixInput)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsByDetailContaining(infixInput).get(0));
    }


    @Test
    public void testListTransactionsByExecutedDate() {
        Date executedDateInput = new Date();
        when(mockTransactionRepository.findByExecutedUTC(executedDateInput)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsOnExecutedDate(executedDateInput).get(0));
    }


    @Test
    public void testListTransactionsByExecutedDateAfter() {
        Date executedDateInput = new Date();
        when(mockTransactionRepository.findByExecutedUTCAfter(executedDateInput)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsOnExecutedDateAfter(executedDateInput).get(0));
    }


    @Test
    public void testListTransactionsByExecutedDateBefore() {
        Date executedDateInput = new Date();
        when(mockTransactionRepository.findByExecutedUTCBefore(executedDateInput)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsOnExecutedDateBefore(executedDateInput).get(0));
    }


    @Test
    public void testListTransactionsByScheduledDate() {
        Date executedDateInput = new Date();
        when(mockTransactionRepository.findByScheduledUTC(executedDateInput)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsOnScheduledDate(executedDateInput).get(0));
    }


    @Test
    public void testListTransactionsByScheduledDateAfter() {
        Date executedDateInput = new Date();
        when(mockTransactionRepository.findByScheduledUTCAfter(executedDateInput)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsByScheduledDateAfter(executedDateInput).get(0));
    }


    @Test
    public void testListTransactionsByScheduledDateBefore() {
        Date executedDateInput = new Date();
        when(mockTransactionRepository.findByScheduledUTCBefore(executedDateInput)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsByScheduledDateBefore(executedDateInput).get(0));
    }


    @Test
    public void testListTransactionsByStatus() {
        TransactionStatusEnum statusInput = TransactionStatusEnum.DELIVERED;
        when(mockTransactionRepository.findByStatus(statusInput)).thenReturn(List.of(transaction1));

        Assert.assertEquals(transaction1, mockTransactionService.listTransactionsByStatus(statusInput).get(0));
    }


//    @Test
//    public void testListTransactionsByStatusContaining() {
//        TransactionStatusEnum infixStatusInput = DELIVERED;
//        when(mockTransactionRepository.findByStatusContaining(infixStatusInput.toString())).thenReturn(List.of(transaction1));
//
//        assertEquals(transaction1, mockTransactionService.listTransactionsByStatusContaining(infixStatusInput).get(0));
//    }


    @Test
    public void testModifyTransaction() throws NoSuchFieldException, IllegalAccessException {
        // testModifyTransaction_notFoundTransaction
        UUID transactionIdInput_notFound = Utils.generateUUID();

        when(mockTransactionRepository.findById(transactionIdInput_notFound)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> mockTransactionService.modifyTransaction(any(Transaction.class), transactionIdInput_notFound));

        // testModifyTransaction_success
        transaction1.setDetail("old detail");
        LocalDateTime localDateTime = LocalDateTime.of(2023, 9, 3, 12, 34);
        transaction1.setExecutedUTC(localDateTime);
        transaction1.setScheduledUTC(localDateTime);
        transaction1.setStatus(TransactionStatusEnum.SCHEDULED);
        transaction1.setCreatedDate(localDateTime);
        transaction1.setLastUpdatedDate(localDateTime);
        UUID transactionId = Utils.generateUUID();

        when(mockTransactionRepository.findById(transactionId)).thenReturn(Optional.ofNullable(transaction1));

        // transaction1 should get modified
        String newRefID = Utils.generateRandomStringAZ09(10);
        LocalDateTime newDateTime = LocalDateTime.of(2022, 8, 16, 23, 45);
        Transaction newTransaction = new Transaction(transaction1.getTransactionId(), newRefID, 3L, 303L,
                4L, 404L, BigDecimal.TEN, TransactionType.DEBIT.getTransactionType(), "new detail", newDateTime,
                newDateTime, TransactionStatusEnum.PROCESSING, newDateTime, newDateTime);

        Transaction modifiedTransaction = mockTransactionService.modifyTransaction(newTransaction, transactionId);

        assertEquals(newTransaction.getTransactionId(), modifiedTransaction.getTransactionId());
        assertEquals(newTransaction.getReferenceId(), modifiedTransaction.getReferenceId());
        assertEquals(newTransaction.getSourceOwnerId(), modifiedTransaction.getSourceOwnerId());
        assertEquals(newTransaction.getSourceAccountId(), modifiedTransaction.getSourceAccountId());
        assertEquals(newTransaction.getDestinedOwnerId(), modifiedTransaction.getDestinedOwnerId());
        assertEquals(newTransaction.getDestinedAccountId(), modifiedTransaction.getDestinedAccountId());
        assertEquals(newTransaction.getAmount(), modifiedTransaction.getAmount());
        assertEquals(newTransaction.getType(), modifiedTransaction.getType());
        assertEquals(newTransaction.getDetail(), modifiedTransaction.getDetail());
        assertEquals(newTransaction.getExecutedUTC(), modifiedTransaction.getExecutedUTC());
        assertEquals(newTransaction.getScheduledUTC(), modifiedTransaction.getScheduledUTC());
        Assert.assertEquals(newTransaction.getStatus(), modifiedTransaction.getStatus());
        assertEquals(transaction1.getCreatedDate(), modifiedTransaction.getCreatedDate());
        assertEquals(transaction1.getLastUpdatedDate(), modifiedTransaction.getLastUpdatedDate());
    }


    @Test
    public void testModifyTransactionStatus() {
        // testModifyTransactionStatus_success
        UUID transactionId = Utils.generateUUID();
        TransactionStatusEnum newTransactionStatusEnum = TransactionStatusEnum.DELIVERED;
        when(mockTransactionRepository.findById(transactionId)).thenReturn(Optional.ofNullable(transaction1));
        Transaction modifiedTransaction = mockTransactionService.modifyTransactionStatus(transactionId, newTransactionStatusEnum);

        Assert.assertEquals(newTransactionStatusEnum, modifiedTransaction.getStatus());

        // testModifyTransactionStatus_transactionNotFound
        UUID transactionIdInput_notFound = Utils.generateUUID();
        when(mockTransactionRepository.findById(transactionIdInput_notFound)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> mockTransactionService.modifyTransactionStatus(transactionIdInput_notFound, any(TransactionStatusEnum.class)));
    }


    @Test
    public void testRemoveTransaction() {
        // testRemoveTransaction_Success
        UUID transactionIdInput = Utils.generateUUID();
        when(mockTransactionRepository.existsById(transactionIdInput)).thenReturn(true);
        when(mockTransactionRepository.findById(transactionIdInput)).thenReturn(Optional.ofNullable(transaction1));

        assertTrue(mockTransactionService.removeTransaction(transactionIdInput));

        // testRemoveTransaction_TransactionIdNotFound_throwResourceNotFoundException
        UUID transactionIdInput_notFound = Utils.generateUUID();
        when(mockTransactionRepository.existsById(transactionIdInput_notFound)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> mockTransactionService.removeTransaction(transactionIdInput_notFound));

        // testRemoveTransaction_FailedCatchException
        UUID transactionIdInput_Exception = Utils.generateUUID();
        when(mockTransactionRepository.existsById(transactionIdInput_Exception)).thenReturn(true);
        doThrow(new RuntimeException("Some exception"))
                .when(mockTransactionRepository).deleteById(transactionIdInput_Exception);
        boolean resultException = mockTransactionService.removeTransaction(transactionIdInput_Exception);

        assertFalse(resultException);
    }


    @Test
    public void testSendMoneyToDifferentOwner() {
//        transaction1.setSourceOwnerId(1L);
//        transaction1.setDestinedOwnerId(1L);
//        transaction1.setStatus("");
//
//        when(mockCustomerRepository.findById(1L)).thenReturn(Optional.ofNullable(customer1));
//        when(mockAccountRepository.findById(101L)).thenReturn(Optional.ofNullable(account1));
//        when(mockAccountService.verifyOwnershipOfAccount(1L, 101L)).thenReturn(account1);
//        when(mockAccountService.verifyOwnershipOfAccount(1L, 102L)).thenReturn(account2);
//
//        Transaction executedTransaction = mockTransactionService.transferMoneySameOwner(transaction1);
//
//        assertEquals("SUCCESS", executedTransaction.getStatus());
    }


    @Test
    public void testTransferMoneySameOwner() {
    }
}
