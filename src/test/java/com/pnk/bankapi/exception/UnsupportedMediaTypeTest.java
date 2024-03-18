package com.pnk.bankapi.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class UnsupportedMediaTypeTest {

    @Test
    public void testUnsupportedMediaType() {
        // Arrange and Act
        UnsupportedMediaType actualUnsupportedMediaType = new UnsupportedMediaType("Not all who wander are lost");

        // Assert
        assertEquals("Not all who wander are lost", actualUnsupportedMediaType.getLocalizedMessage());
        assertEquals("Not all who wander are lost", actualUnsupportedMediaType.getMessage());
        assertNull(actualUnsupportedMediaType.getCause());
        assertEquals(0, actualUnsupportedMediaType.getSuppressed().length);
    }

}