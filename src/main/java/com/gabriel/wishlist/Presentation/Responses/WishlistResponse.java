package com.gabriel.wishlist.Presentation.Responses;

import com.gabriel.wishlist.Domain.Entities.Wishlist;

import java.util.Set;

public record WishlistResponse(
        String id,
        String customerId,
        Set<String> productIds
) {
    public static WishlistResponse fromEntity(Wishlist entity){
        if(entity == null) return null;
        return new WishlistResponse(
                entity.getId(),
                entity.getCustomerId(),
                entity.getProductIds()
        );
    }
}
