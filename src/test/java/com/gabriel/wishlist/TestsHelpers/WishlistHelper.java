package com.gabriel.wishlist.TestsHelpers;

import com.gabriel.wishlist.Presentation.Responses.WishlistResponse;
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

    public static WishlistResponse ToDTO(Wishlist entity) {
        return new WishlistResponse(
                entity.getId(),
                entity.getCustomerId(),
                entity.getProductIds()
        );
    }

    public static WishlistResponse BuildWishlistDTO(String customer, String product){
        return new WishlistResponse("ID",customer, Set.of(product));
    }

    public static WishlistResponse BuildWishlistDTOWithProducts(String customer, int quantityProducts) {
        Wishlist wishlist = new Wishlist(customer);
        for (int i = 0; i < quantityProducts; i++) {
            wishlist.addProduct("product" + i);
        }
        return ToDTO(wishlist);
    }
}
