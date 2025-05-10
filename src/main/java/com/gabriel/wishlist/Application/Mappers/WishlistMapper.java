package com.gabriel.wishlist.Application.Mappers;

import com.gabriel.wishlist.Application.Interfaces.Mappers.IWishlistMapper;
import com.gabriel.wishlist.Application.Models.WishlistDTO;
import com.gabriel.wishlist.Domain.Entities.Wishlist;
import org.springframework.stereotype.Component;

@Component
public class WishlistMapper implements IWishlistMapper {

    @Override
    public WishlistDTO ToWishlistDTO(Wishlist entity) {
        return new WishlistDTO(
                entity.getId(),
                entity.getCustomerId(),
                entity.getProductIds()
        );
    }
}
