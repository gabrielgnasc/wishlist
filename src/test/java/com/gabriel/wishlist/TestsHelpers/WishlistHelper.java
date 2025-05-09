package com.gabriel.wishlist.TestsHelpers;

import com.gabriel.wishlist.Domain.Entities.Wishlist;

public class WishlistHelper {

    public static Wishlist BuildWishlist(String customer){
        Wishlist sample = new Wishlist();
        sample.setCustomerId(customer);
        return sample;
    }
}
