package com.pnk.bankapi.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class AuthExceptionTest {

    @Test
    public void testNewAuthException() {
        // Arrange and Act
        AuthException actualAuthException = new AuthException("0123456789ABCDEF");

        // Assert
        assertEquals("0123456789ABCDEF", actualAuthException.getLocalizedMessage());
        assertEquals("0123456789ABCDEF", actualAuthException.getMessage());
        assertNull(actualAuthException.getCause());
        assertEquals(0, actualAuthException.getSuppressed().length);
    }

}