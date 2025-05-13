package com.gabriel.wishlist.Common.Handlers;

import com.gabriel.wishlist.Common.Exceptions.WishlistFullException;
import com.gabriel.wishlist.Common.Exceptions.WishlistNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(WishlistNotFoundException.class)
    public ResponseEntity<HashMap> handleWishlistNotFound(WishlistNotFoundException ex) {

        return ResponseEntity.status(404)
                .body(buildErrorMessage(404, ex.getMessage()));
    }

    @ExceptionHandler(WishlistFullException.class)
    public ResponseEntity<HashMap> handleWishlistFull(WishlistFullException ex) {

        return ResponseEntity.status(400)
                .body(buildErrorMessage(400, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HashMap> handleGenericException(Exception ex) {

        return ResponseEntity.status(500)
                .body(buildErrorMessage(500, ex.getMessage()));
    }

    private HashMap<String, Object> buildErrorMessage(int status, String message){
        logger.error(message);
        HashMap<String, Object> messageObj = new HashMap();
        messageObj.put("statusCode", status);
        messageObj.put("message", message);
        messageObj.put("timestamp", LocalDateTime.now().toString());

        return messageObj;
    }
}