package com.pnk.bankapi.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AccountStatusEnumTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testAccountStatusEnum() {
        assertEquals("DELETED", AccountStatusEnum.ACCOUNT_STATUS_DELETED);
        assertEquals("DEACTIVATED", AccountStatusEnum.ACCOUNT_STATUS_DEACTIVATED);
        assertEquals("ACTIVATED", AccountStatusEnum.ACCOUNT_STATUS_ACTIVATED);
        assertEquals("FROZE", AccountStatusEnum.ACCOUNT_STATUS_FROZE);

        assertArrayEquals(new String[]{"DELETED", "DEACTIVATED", "ACTIVATED", "FROZE"}, AccountStatusEnum.allAccountStatuses);
        assertArrayEquals(new String[]{"DELETED", "DEACTIVATED", "ACTIVATED", "FROZE"}, AccountStatusEnum.allAccountStatusesToModifyAccounts);
    }
}
