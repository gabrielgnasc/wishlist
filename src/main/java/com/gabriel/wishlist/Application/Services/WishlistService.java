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
    public Wishlist addProduct(String costumerId, String productId) {
        Wishlist wishlist = wishlistRepository
                .findByCustomerId(costumerId)
                .orElse(new Wishlist(costumerId));

        wishlist.addProduct(productId);

        return wishlistRepository.save(wishlist);
    }
}
