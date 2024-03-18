package com.pnk.bankapi.exception;

import org.junit.Test;

import static org.junit.Assert.*;


public class CustomerNotFoundExceptionTest {

    /**
     * Method under test:
     * {@link CustomerNotFoundException#CustomerNotFoundException()}
     */
    @Test
    public void testNewCustomerNotFoundException() {
        // Arrange and Act
        CustomerNotFoundException actualCustomerNotFoundException = new CustomerNotFoundException();

        // Assert
        assertNull(actualCustomerNotFoundException.getLocalizedMessage());
        assertNull(actualCustomerNotFoundException.getMessage());
        assertNull(actualCustomerNotFoundException.getCause());
        assertEquals(0, actualCustomerNotFoundException.getSuppressed().length);
    }

    /**
     * Method under test:
     * {@link CustomerNotFoundException#CustomerNotFoundException(String)}
     */
    @Test
    public void testNewCustomerNotFoundException2() {
        // Arrange and Act
        CustomerNotFoundException actualCustomerNotFoundException = new CustomerNotFoundException("An error occurred");

        // Assert
        assertEquals("An error occurred", actualCustomerNotFoundException.getLocalizedMessage());
        assertEquals("An error occurred", actualCustomerNotFoundException.getMessage());
        assertNull(actualCustomerNotFoundException.getCause());
        assertEquals(0, actualCustomerNotFoundException.getSuppressed().length);
    }

    /**
     * Method under test:
     * {@link CustomerNotFoundException#CustomerNotFoundException(String, Throwable)}
     */
    @Test
    public void testNewCustomerNotFoundException3() {
        // Arrange
        Throwable cause = new Throwable();

        // Act
        CustomerNotFoundException actualCustomerNotFoundException = new CustomerNotFoundException("An error occurred",
                cause);

        // Assert
        assertEquals("An error occurred", actualCustomerNotFoundException.getLocalizedMessage());
        assertEquals("An error occurred", actualCustomerNotFoundException.getMessage());
        Throwable cause2 = actualCustomerNotFoundException.getCause();
        assertNull(cause2.getLocalizedMessage());
        assertNull(cause2.getMessage());
        assertNull(cause2.getCause());
        Throwable[] suppressed = actualCustomerNotFoundException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertSame(cause, cause2);
        assertSame(suppressed, cause2.getSuppressed());
    }

    /**
     * Method under test:
     * {@link CustomerNotFoundException#CustomerNotFoundException(String, Throwable, boolean, boolean)}
     */
    @Test
    public void testNewCustomerNotFoundException4() {
        // Arrange
        Throwable cause = new Throwable();

        // Act
        CustomerNotFoundException actualCustomerNotFoundException = new CustomerNotFoundException("An error occurred",
                cause, true, true);

        // Assert
        assertEquals("An error occurred", actualCustomerNotFoundException.getLocalizedMessage());
        assertEquals("An error occurred", actualCustomerNotFoundException.getMessage());
        Throwable cause2 = actualCustomerNotFoundException.getCause();
        assertNull(cause2.getLocalizedMessage());
        assertNull(cause2.getMessage());
        assertNull(cause2.getCause());
        Throwable[] suppressed = actualCustomerNotFoundException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertSame(cause, cause2);
        assertSame(suppressed, cause2.getSuppressed());
    }

    /**
     * Method under test:
     * {@link CustomerNotFoundException#CustomerNotFoundException(Throwable)}
     */
    @Test
    public void testNewCustomerNotFoundException5() {
        // Arrange
        Throwable cause = new Throwable();

        // Act
        CustomerNotFoundException actualCustomerNotFoundException = new CustomerNotFoundException(cause);

        // Assert
        assertEquals("java.lang.Throwable", actualCustomerNotFoundException.getLocalizedMessage());
        assertEquals("java.lang.Throwable", actualCustomerNotFoundException.getMessage());
        Throwable cause2 = actualCustomerNotFoundException.getCause();
        assertNull(cause2.getLocalizedMessage());
        assertNull(cause2.getMessage());
        assertNull(cause2.getCause());
        Throwable[] suppressed = actualCustomerNotFoundException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertSame(cause, cause2);
        assertSame(suppressed, cause2.getSuppressed());
    }

}