package com.gabriel.wishlist.Common.Exceptions;

import com.gabriel.wishlist.Common.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WishlistFullException extends RuntimeException {

    public WishlistFullException(String message) {
        super(message);
    }

    public WishlistFullException() {
        super(Constants.ErrorMessage.WISHLIST_EXCEEDED_0_PRODUCTS
                .replace("{0}", String.valueOf(Constants.MAX_LIMIT_WISHLIST)));
    }
}
