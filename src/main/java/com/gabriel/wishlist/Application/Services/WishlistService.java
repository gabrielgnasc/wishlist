package com.gabriel.wishlist.Application.Services;

import com.gabriel.wishlist.Application.Interfaces.Services.IWishlistService;
import com.gabriel.wishlist.Common.Exceptions.WishlistNotFoundException;
import com.gabriel.wishlist.Domain.Entities.Wishlist;
import com.gabriel.wishlist.Domain.Repositories.IWishlistRepository;

import java.util.HashSet;
import java.util.Set;

public class WishlistService implements IWishlistService {

    private final IWishlistRepository wishlistRepository;

    public WishlistService(IWishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }


    @Override
    public Wishlist addProduct(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository
                .findByCustomerId(customerId)
                .orElse(new Wishlist(customerId));

        wishlist.addProduct(productId);

        return wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist removeProduct(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository
                .findByCustomerId(customerId)
                .orElseThrow(WishlistNotFoundException::new);

        wishlist.removeProduct(productId);

        return wishlistRepository.save(wishlist);
    }

    @Override
    public Set<String> listProducts(String customerId) {
        return wishlistRepository
                .findByCustomerId(customerId)
                .map(Wishlist::getProductIds)
                .orElse(new HashSet<>());
    }
}
