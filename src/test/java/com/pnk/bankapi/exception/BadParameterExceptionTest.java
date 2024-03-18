package com.pnk.bankapi.exception;

import org.junit.Test;

import static org.junit.Assert.*;


public class BadParameterExceptionTest {

    /**
     * Method under test: {@link BadParameterException#BadParameterException()}
     */
    @Test
    public void testNewBadParameterException() {
        // Arrange and Act
        BadParameterException actualBadParameterException = new BadParameterException();

        // Assert
        assertNull(actualBadParameterException.getLocalizedMessage());
        assertNull(actualBadParameterException.getMessage());
        assertNull(actualBadParameterException.getCause());
        assertEquals(0, actualBadParameterException.getSuppressed().length);
    }

    /**
     * Method under test:
     * {@link BadParameterException#BadParameterException(String)}
     */
    @Test
    public void testNewBadParameterException2() {
        // Arrange and Act
        BadParameterException actualBadParameterException = new BadParameterException("An error occurred");

        // Assert
        assertEquals("An error occurred", actualBadParameterException.getLocalizedMessage());
        assertEquals("An error occurred", actualBadParameterException.getMessage());
        assertNull(actualBadParameterException.getCause());
        assertEquals(0, actualBadParameterException.getSuppressed().length);
    }

    /**
     * Method under test:
     * {@link BadParameterException#BadParameterException(String, Throwable)}
     */
    @Test
    public void testNewBadParameterException3() {
        // Arrange
        Throwable cause = new Throwable();

        // Act
        BadParameterException actualBadParameterException = new BadParameterException("An error occurred", cause);

        // Assert
        assertEquals("An error occurred", actualBadParameterException.getLocalizedMessage());
        assertEquals("An error occurred", actualBadParameterException.getMessage());
        Throwable cause2 = actualBadParameterException.getCause();
        assertNull(cause2.getLocalizedMessage());
        assertNull(cause2.getMessage());
        assertNull(cause2.getCause());
        Throwable[] suppressed = actualBadParameterException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertSame(cause, cause2);
        assertSame(suppressed, cause2.getSuppressed());
    }

    /**
     * Method under test:
     * {@link BadParameterException#BadParameterException(String, Throwable, boolean, boolean)}
     */
    @Test
    public void testNewBadParameterException4() {
        // Arrange
        Throwable cause = new Throwable();

        // Act
        BadParameterException actualBadParameterException = new BadParameterException("An error occurred", cause, true,
                true);

        // Assert
        assertEquals("An error occurred", actualBadParameterException.getLocalizedMessage());
        assertEquals("An error occurred", actualBadParameterException.getMessage());
        Throwable cause2 = actualBadParameterException.getCause();
        assertNull(cause2.getLocalizedMessage());
        assertNull(cause2.getMessage());
        assertNull(cause2.getCause());
        Throwable[] suppressed = actualBadParameterException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertSame(cause, cause2);
        assertSame(suppressed, cause2.getSuppressed());
    }

    /**
     * Method under test:
     * {@link BadParameterException#BadParameterException(Throwable)}
     */
    @Test
    public void testNewBadParameterException5() {
        // Arrange
        Throwable cause = new Throwable();

        // Act
        BadParameterException actualBadParameterException = new BadParameterException(cause);

        // Assert
        assertEquals("java.lang.Throwable", actualBadParameterException.getLocalizedMessage());
        assertEquals("java.lang.Throwable", actualBadParameterException.getMessage());
        Throwable cause2 = actualBadParameterException.getCause();
        assertNull(cause2.getLocalizedMessage());
        assertNull(cause2.getMessage());
        assertNull(cause2.getCause());
        Throwable[] suppressed = actualBadParameterException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertSame(cause, cause2);
        assertSame(suppressed, cause2.getSuppressed());
    }

}