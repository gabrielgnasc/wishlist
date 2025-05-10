package com.gabriel.wishlist.TestsHelpers;

import com.gabriel.wishlist.Application.Models.WishlistDTO;
import com.gabriel.wishlist.Domain.Entities.Wishlist;

import java.util.Set;

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

    public static WishlistDTO ToDTO(Wishlist entity) {
        return new WishlistDTO(
                entity.getId(),
                entity.getCustomerId(),
                entity.getProductIds()
        );
    }

    public static WishlistDTO BuildWishlistDTO(String customer, String product){
        return new WishlistDTO("ID",customer, Set.of(product));
    }

    public static WishlistDTO BuildWishlistDTOWithProducts(String customer, int quantityProducts) {
        Wishlist wishlist = new Wishlist(customer);
        for (int i = 0; i < quantityProducts; i++) {
            wishlist.addProduct("product" + i);
        }
        return ToDTO(wishlist);
    }
}
