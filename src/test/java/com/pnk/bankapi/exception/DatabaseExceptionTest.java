package com.pnk.bankapi.exception;

import org.junit.Test;

import static org.junit.Assert.*;


public class DatabaseExceptionTest {

    /**
     * Method under test: {@link DatabaseException#DatabaseException()}
     */
    @Test
    public void testNewDatabaseException() {
        // Arrange and Act
        DatabaseException actualDatabaseException = new DatabaseException();

        // Assert
        assertNull(actualDatabaseException.getLocalizedMessage());
        assertNull(actualDatabaseException.getMessage());
        assertNull(actualDatabaseException.getCause());
        assertEquals(0, actualDatabaseException.getSuppressed().length);
    }

    /**
     * Method under test: {@link DatabaseException#DatabaseException(String)}
     */
    @Test
    public void testNewDatabaseException2() {
        // Arrange and Act
        DatabaseException actualDatabaseException = new DatabaseException("An error occurred");

        // Assert
        assertEquals("An error occurred", actualDatabaseException.getLocalizedMessage());
        assertEquals("An error occurred", actualDatabaseException.getMessage());
        assertNull(actualDatabaseException.getCause());
        assertEquals(0, actualDatabaseException.getSuppressed().length);
    }

    /**
     * Method under test:
     * {@link DatabaseException#DatabaseException(String, Throwable)}
     */
    @Test
    public void testNewDatabaseException3() {
        // Arrange
        Throwable cause = new Throwable();

        // Act
        DatabaseException actualDatabaseException = new DatabaseException("An error occurred", cause);

        // Assert
        assertEquals("An error occurred", actualDatabaseException.getLocalizedMessage());
        assertEquals("An error occurred", actualDatabaseException.getMessage());
        Throwable cause2 = actualDatabaseException.getCause();
        assertNull(cause2.getLocalizedMessage());
        assertNull(cause2.getMessage());
        assertNull(cause2.getCause());
        Throwable[] suppressed = actualDatabaseException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertSame(cause, cause2);
        assertSame(suppressed, cause2.getSuppressed());
    }

    /**
     * Method under test:
     * {@link DatabaseException#DatabaseException(String, Throwable, boolean, boolean)}
     */
    @Test
    public void testNewDatabaseException4() {
        // Arrange
        Throwable cause = new Throwable();

        // Act
        DatabaseException actualDatabaseException = new DatabaseException("An error occurred", cause, true, true);

        // Assert
        assertEquals("An error occurred", actualDatabaseException.getLocalizedMessage());
        assertEquals("An error occurred", actualDatabaseException.getMessage());
        Throwable cause2 = actualDatabaseException.getCause();
        assertNull(cause2.getLocalizedMessage());
        assertNull(cause2.getMessage());
        assertNull(cause2.getCause());
        Throwable[] suppressed = actualDatabaseException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertSame(cause, cause2);
        assertSame(suppressed, cause2.getSuppressed());
    }

    /**
     * Method under test: {@link DatabaseException#DatabaseException(Throwable)}
     */
    @Test
    public void testNewDatabaseException5() {
        // Arrange
        Throwable cause = new Throwable();

        // Act
        DatabaseException actualDatabaseException = new DatabaseException(cause);

        // Assert
        assertEquals("java.lang.Throwable", actualDatabaseException.getLocalizedMessage());
        assertEquals("java.lang.Throwable", actualDatabaseException.getMessage());
        Throwable cause2 = actualDatabaseException.getCause();
        assertNull(cause2.getLocalizedMessage());
        assertNull(cause2.getMessage());
        assertNull(cause2.getCause());
        Throwable[] suppressed = actualDatabaseException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertSame(cause, cause2);
        assertSame(suppressed, cause2.getSuppressed());
    }

}