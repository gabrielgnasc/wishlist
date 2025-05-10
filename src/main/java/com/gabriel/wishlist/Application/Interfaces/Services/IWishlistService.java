package com.gabriel.wishlist.Application.Interfaces.Services;

import com.gabriel.wishlist.Domain.Entities.Wishlist;

import java.util.Set;

public interface IWishlistService {
    Wishlist addProduct(String costumerId, String productId);

    Wishlist removeProduct(String customerId, String productId);

    Set<String> listProducts(String customerId);

    boolean containsProduct(String customerId, String productId);
}
