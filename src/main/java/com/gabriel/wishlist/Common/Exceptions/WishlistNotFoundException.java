package com.gabriel.wishlist.Common.Exceptions;

import com.gabriel.wishlist.Common.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WishlistNotFoundException extends RuntimeException {

    public WishlistNotFoundException(String message) {
        super(message);
    }

    public WishlistNotFoundException() {
        super(Constants.ErrorMessage.WISHLIST_OF_CUSTOMER_NOT_FOUND);
    }
}