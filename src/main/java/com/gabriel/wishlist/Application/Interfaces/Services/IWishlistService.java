package com.gabriel.wishlist.Application.Interfaces.Services;

import com.gabriel.wishlist.Application.Models.WishlistDTO;

import java.util.Set;

public interface IWishlistService {
    WishlistDTO addProduct(String costumerId, String productId);

    WishlistDTO removeProduct(String customerId, String productId);

    Set<String> listProducts(String customerId);

    boolean containsProduct(String customerId, String productId);
}
