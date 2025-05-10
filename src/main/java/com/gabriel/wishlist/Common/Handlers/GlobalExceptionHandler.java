package com.gabriel.wishlist.Common.Handlers;

import com.gabriel.wishlist.Common.Exceptions.WishlistFullException;
import com.gabriel.wishlist.Common.Exceptions.WishlistNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WishlistNotFoundException.class)
    public ResponseEntity<String> handleWishlistNotFound(WishlistNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(WishlistFullException.class)
    public ResponseEntity<String> handleWishlistFull(WishlistFullException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }
}