package com.gabriel.wishlist.Application.Services;

import com.gabriel.wishlist.Application.Interfaces.Services.IWishlistService;
import com.gabriel.wishlist.Domain.Entities.Wishlist;
import com.gabriel.wishlist.Domain.Repositories.IWishlistRepository;

import java.util.Optional;
import java.util.Set;

public class WishlistService implements IWishlistService {

    private final IWishlistRepository wishlistRepository;

    public WishlistService(IWishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }


    @Override
    public Wishlist addProduct(String costumerId, String productId) {
        Optional<Wishlist> wishlist = wishlistRepository.findByCustomerId(costumerId);

        if(wishlist.isEmpty()){

            Wishlist createdWishlist = Wishlist
                    .builder()
                    .customerId(costumerId)
                    .productIds(Set.of(productId))
                    .build();

            return wishlistRepository.save(createdWishlist);
        }
            wishlist.get()
                    .addProduct(productId);

            return wishlistRepository.save(wishlist.get());
    }
}
