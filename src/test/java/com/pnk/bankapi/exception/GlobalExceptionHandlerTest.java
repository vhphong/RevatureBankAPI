package com.pnk.bankapi.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class GlobalExceptionHandlerTest {

    @Test
    void testResourceNotFoundException() {
        // Arrange
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ResourceNotFoundException ex = new ResourceNotFoundException("An error occurred");

        // Act
        ResponseEntity<?> actualResourceNotFoundExceptionResult = globalExceptionHandler.resourceNotFoundException(ex,
                new ServletWebRequest(new MockHttpServletRequest()));

        // Assert
        assertEquals("An error occurred", ((ErrorDetails) actualResourceNotFoundExceptionResult.getBody()).getMessage());
        assertEquals("uri=", ((ErrorDetails) actualResourceNotFoundExceptionResult.getBody()).getDetails());
        assertEquals(404, actualResourceNotFoundExceptionResult.getStatusCodeValue());
        assertTrue(actualResourceNotFoundExceptionResult.hasBody());
        assertTrue(actualResourceNotFoundExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testGlobeExceptionHandler() {
        // Arrange
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        Exception ex = new Exception("foo");

        // Act
        ResponseEntity<?> actualGlobeExceptionHandlerResult = globalExceptionHandler.globeExceptionHandler(ex,
                new ServletWebRequest(new MockHttpServletRequest()));

        // Assert
        assertEquals("foo", ((ErrorDetails) actualGlobeExceptionHandlerResult.getBody()).getMessage());
        assertEquals("uri=", ((ErrorDetails) actualGlobeExceptionHandlerResult.getBody()).getDetails());
        assertEquals(500, actualGlobeExceptionHandlerResult.getStatusCodeValue());
        assertTrue(actualGlobeExceptionHandlerResult.hasBody());
        assertTrue(actualGlobeExceptionHandlerResult.getHeaders().isEmpty());
    }
}