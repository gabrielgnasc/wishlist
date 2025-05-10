package com.gabriel.wishlist.Application.Interfaces.Mappers;

import com.gabriel.wishlist.Application.Models.WishlistDTO;
import com.gabriel.wishlist.Domain.Entities.Wishlist;

public interface IWishlistMapper {
    WishlistDTO ToWishlistDTO(Wishlist entity);
}
