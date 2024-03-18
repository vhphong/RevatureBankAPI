package com.pnk.bankapi.controller;

import com.pnk.bankapi.model.Transaction;
import com.pnk.bankapi.service.TransactionService;
import com.pnk.bankapi.util.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static org.mockito.Mockito.when;

@WebMvcTest(TransactionController.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionControllerTest {

    @Autowired
    private TransactionController mockTransactionController;
    @MockBean
    private TransactionService mockTransactionService;
    @Autowired
    private MockMvc mockMvc;

    @Mock
    AutoCloseable autoCloseable;
    Transaction transaction1, transaction2;
    Date date1;

    @Before
    public void setUp() throws Exception {
        autoCloseable = MockitoAnnotations.openMocks(this);
        mockTransactionController = new TransactionController(mockTransactionService);

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        String dateInString1 = "03-28-2022";
        date1 = formatter.parse(dateInString1);
        UUID transactionUuid1 = Utils.generateUUID();
        transaction1 = new Transaction(transactionUuid1, Utils.generateRandomStringAZ09(10), 1L, 101L, 2L, 201L, BigDecimal.ONE);
        transaction2 = new Transaction(1L, 101L, 2L, 202L, BigDecimal.ONE);

        // arrange
        when(mockTransactionService.insertTransaction(transaction1)).thenReturn(transaction1);
    }

    @After
    public void tearDown() throws Exception {
        transaction1 = null;
        transaction2 = null;
        autoCloseable.close();
    }


    @Test
    public void testSaveTransaction() {
    }


    @Test
    public void testSendMoneyBetweenTwoAccounts() {
    }


    @Test
    public void testTransferMoneyBetweenSameOwnerAccounts() {
    }


    @Test
    public void testDeleteTransaction() {
    }
}