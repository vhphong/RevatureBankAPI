package com.pnk.bankapi.exception;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;


public class ErrorDetailsTest {

    /**
     * Method under test: {@link ErrorDetails#ErrorDetails(Date, String, String)}
     */
    @Test
    public void testNewErrorDetails() {
        // Arrange
        Date timestamp = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

        // Act
        ErrorDetails actualErrorDetails = new ErrorDetails(timestamp, "Not all who wander are lost", "Details");

        // Assert
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date timestamp2 = actualErrorDetails.getTimestamp();
        assertEquals("1969-12-31", simpleDateFormat.format(timestamp2));
        assertEquals("Details", actualErrorDetails.getDetails());
        assertEquals("Not all who wander are lost", actualErrorDetails.getMessage());
        assertSame(timestamp, timestamp2);
    }

}