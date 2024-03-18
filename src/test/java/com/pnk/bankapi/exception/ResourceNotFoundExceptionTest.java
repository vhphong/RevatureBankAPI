package com.pnk.bankapi.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class ResourceNotFoundExceptionTest {

    @Test
    public void testNewResourceNotFoundException() {
        // Arrange and Act
        ResourceNotFoundException actualResourceNotFoundException = new ResourceNotFoundException("An error occurred");

        // Assert
        assertEquals("An error occurred", actualResourceNotFoundException.getLocalizedMessage());
        assertEquals("An error occurred", actualResourceNotFoundException.getMessage());
        assertNull(actualResourceNotFoundException.getCause());
        assertEquals(0, actualResourceNotFoundException.getSuppressed().length);
    }

}