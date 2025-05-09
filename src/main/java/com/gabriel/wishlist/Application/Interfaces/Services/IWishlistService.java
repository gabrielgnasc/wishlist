package com.gabriel.wishlist.Application.Interfaces.Services;

import com.gabriel.wishlist.Domain.Entities.Wishlist;

public interface IWishlistService {
    public Wishlist addProduct(String costumerId, String productId);
}
