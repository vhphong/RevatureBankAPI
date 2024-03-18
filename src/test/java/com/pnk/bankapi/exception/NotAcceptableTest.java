package com.pnk.bankapi.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class NotAcceptableTest {

    @Test
    public void testNotAcceptable() {
        // Arrange and Act
        NotAcceptable actualNotAcceptable = new NotAcceptable("Not all who wander are lost");

        // Assert
        assertEquals("Not all who wander are lost", actualNotAcceptable.getLocalizedMessage());
        assertEquals("Not all who wander are lost", actualNotAcceptable.getMessage());
        assertNull(actualNotAcceptable.getCause());
        assertEquals(0, actualNotAcceptable.getSuppressed().length);
    }

}