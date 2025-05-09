package com.gabriel.wishlist.Application.Services;

import com.gabriel.wishlist.Application.Interfaces.Services.IWishlistService;
import com.gabriel.wishlist.Domain.Entities.Wishlist;
import com.gabriel.wishlist.Domain.Repositories.IWishlistRepository;

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
}
