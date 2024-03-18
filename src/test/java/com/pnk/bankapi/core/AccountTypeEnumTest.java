package com.pnk.bankapi.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTypeEnumTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testAccountTypeEnum() {
        assertEquals("DEBIT", AccountTypeEnum.ACCOUNT_TYPE_DEBIT);
        assertEquals("CREDIT", AccountTypeEnum.ACCOUNT_TYPE_CREDIT);
        assertEquals("SAVING", AccountTypeEnum.ACCOUNT_TYPE_SAVING);

        String[] expected = {"DEBIT", "CREDIT", "SAVING"};
        assertArrayEquals(expected, AccountTypeEnum.allAccountTypes);
        assertArrayEquals(expected, AccountTypeEnum.allAccountTypesToOpenAccounts);

        assertEquals(6, AccountTypeEnum.NUMBER_OF_ACCOUNT_TYPES);

        assertNotNull(AccountTypeEnum.allAccountTypeValuesAsList);
    }
}
