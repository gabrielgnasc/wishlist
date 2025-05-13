package com.gabriel.wishlist.Common.Handlers;

import com.gabriel.wishlist.Common.Exceptions.WishlistFullException;
import com.gabriel.wishlist.Common.Exceptions.WishlistNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GlobalExceptionHandlerTest {
    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleWishlistNotFound_shouldReturn404() {
        // Arrange
        String errorMessage = "Wishlist not found!";
        WishlistNotFoundException exception = new WishlistNotFoundException(errorMessage);

        // Act
        ResponseEntity<HashMap> response = exceptionHandler.handleWishlistNotFound(exception);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().get("statusCode"));
        assertEquals(errorMessage, response.getBody().get("message"));
        assertNotNull(response.getBody().get("timestamp"));
    }

    @Test
    void handleWishlistFull_shouldReturn400() {
        // Arrange
        String errorMessage = "Wishlist is full!";
        WishlistFullException exception = new WishlistFullException(errorMessage);

        // Act
        ResponseEntity<HashMap> response = exceptionHandler.handleWishlistFull(exception);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().get("statusCode"));
        assertEquals(errorMessage, response.getBody().get("message"));
        assertNotNull(response.getBody().get("timestamp"));
    }

    @Test
    void handleGenericException_shouldReturn500() {
        // Arrange
        String errorMessage = "Unexpected error occurred!";
        Exception exception = new Exception(errorMessage);

        // Act
        ResponseEntity<HashMap> response = exceptionHandler.handleGenericException(exception);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().get("statusCode"));
        assertEquals(errorMessage, response.getBody().get("message"));
        assertNotNull(response.getBody().get("timestamp"));
    }
}
