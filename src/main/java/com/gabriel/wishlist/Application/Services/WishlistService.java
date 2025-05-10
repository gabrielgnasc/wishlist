package com.gabriel.wishlist.Application.Services;

import com.gabriel.wishlist.Application.Interfaces.Mappers.IWishlistMapper;
import com.gabriel.wishlist.Application.Interfaces.Services.IWishlistService;
import com.gabriel.wishlist.Application.Models.WishlistDTO;
import com.gabriel.wishlist.Common.Exceptions.WishlistNotFoundException;
import com.gabriel.wishlist.Domain.Entities.Wishlist;
import com.gabriel.wishlist.Domain.Repositories.IWishlistRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class WishlistService implements IWishlistService {

    private final IWishlistRepository wishlistRepository;
    private final IWishlistMapper mapper;

    public WishlistService(IWishlistRepository wishlistRepository, IWishlistMapper mapper) {
        this.wishlistRepository = wishlistRepository;
        this.mapper = mapper;
    }


    @Override
    public WishlistDTO addProduct(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository
                .findByCustomerId(customerId)
                .orElse(new Wishlist(customerId));

        wishlist.addProduct(productId);

        return mapper
                .ToWishlistDTO(wishlistRepository.save(wishlist));
    }

    @Override
    public WishlistDTO removeProduct(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository
                .findByCustomerId(customerId)
                .orElseThrow(WishlistNotFoundException::new);

        wishlist.removeProduct(productId);

        return mapper
                .ToWishlistDTO(wishlistRepository.save(wishlist));
    }

    @Override
    public Set<String> listProducts(String customerId) {
        return wishlistRepository
                .findByCustomerId(customerId)
                .map(Wishlist::getProductIds)
                .orElse(new HashSet<>());
    }

    @Override
    public boolean containsProduct(String customerId, String productId) {
        return wishlistRepository
                .existsByCustomerIdAndProductIds(customerId, productId);
    }
}
