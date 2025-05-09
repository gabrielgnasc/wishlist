package com.gabriel.wishlist.TestsHelpers;

import com.gabriel.wishlist.Domain.Entities.Wishlist;

public class WishlistHelper {

    public static Wishlist BuildWishlist(String customer){
        return new Wishlist(customer);
    }

    public static Wishlist BuildWishlistWithProducts(String customer, int quantityProducts) {
        Wishlist wishlist = new Wishlist(customer);
        for (int i = 0; i < quantityProducts; i++) {
            wishlist.addProduct("product" + i);
        }
        return wishlist;
    }
}
