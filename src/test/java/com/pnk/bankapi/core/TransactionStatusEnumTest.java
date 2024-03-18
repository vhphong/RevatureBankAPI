package com.pnk.bankapi.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionStatusEnumTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testTransactionStatusValues() {
        assertEquals(-1, TransactionStatusEnum.DELETED.getValue());
        assertEquals("DELETED", TransactionStatusEnum.DELETED.getStatus());

        assertEquals(0, TransactionStatusEnum.UNCOMPLETED.getValue());
        assertEquals("UNCOMPLETED", TransactionStatusEnum.UNCOMPLETED.getStatus());

        assertEquals(1, TransactionStatusEnum.SCHEDULED.getValue());
        assertEquals("SCHEDULED", TransactionStatusEnum.SCHEDULED.getStatus());

        assertEquals(2, TransactionStatusEnum.PROCESSING.getValue());
        assertEquals("PROCESSING", TransactionStatusEnum.PROCESSING.getStatus());

        assertEquals(3, TransactionStatusEnum.COMPLETED.getValue());
        assertEquals("COMPLETED", TransactionStatusEnum.COMPLETED.getStatus());

        assertEquals(4, TransactionStatusEnum.DELIVERED.getValue());
        assertEquals("DELIVERED", TransactionStatusEnum.DELIVERED.getStatus());
    }
}
