package com.gabriel.wishlist.Application.Services;

import com.gabriel.wishlist.Application.Interfaces.ExternalServices.IDistributedLock;
import com.gabriel.wishlist.Application.Interfaces.Mappers.IWishlistMapper;
import com.gabriel.wishlist.Application.Interfaces.Services.IWishlistService;
import com.gabriel.wishlist.Application.Models.WishlistDTO;
import com.gabriel.wishlist.Common.Constants;
import com.gabriel.wishlist.Common.Exceptions.ResourceConflictException;
import com.gabriel.wishlist.Common.Exceptions.WishlistNotFoundException;
import com.gabriel.wishlist.Domain.Entities.Wishlist;
import com.gabriel.wishlist.Domain.Repositories.IWishlistRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WishlistService implements IWishlistService {

    private final IWishlistRepository wishlistRepository;
    private final IWishlistMapper mapper;
    private final IDistributedLock distributedLock;

    public WishlistService(IWishlistRepository wishlistRepository, IWishlistMapper mapper, IDistributedLock distributedLock) {
        this.wishlistRepository = wishlistRepository;
        this.mapper = mapper;
        this.distributedLock = distributedLock;
    }


    @Override
    public WishlistDTO addProduct(String customerId, String productId) {
        acquireLock(customerId);
        try{

            Wishlist wishlist = wishlistRepository
                    .findByCustomerId(customerId)
                    .orElse(new Wishlist(customerId));

            wishlist.addProduct(productId);

            return mapper
                    .ToWishlistDTO(wishlistRepository.save(wishlist));
        }finally {
            releaseLock(customerId);
        }
    }

    @Override
    public WishlistDTO removeProduct(String customerId, String productId) {
        acquireLock(customerId);
        try{

            Wishlist wishlist = wishlistRepository
                    .findByCustomerId(customerId)
                    .orElseThrow(WishlistNotFoundException::new);

            wishlist.removeProduct(productId);

            return mapper
                    .ToWishlistDTO(wishlistRepository.save(wishlist));
        }finally {
            releaseLock(customerId);
        }
    }

    @Override
    public Set<String> listProducts(String customerId) {
        return wishlistRepository
                .findByCustomerId(customerId)
                .orElseThrow(WishlistNotFoundException::new)
                .getProductIds();

    }

    @Override
    public boolean containsProduct(String customerId, String productId) {
        return wishlistRepository
                .existsByCustomerIdAndProductIdsContains(customerId, productId);
    }

    private void acquireLock(String customerId){

        if(!distributedLock.acquireLock(
                Constants.LOCK_CUSTOMER_PREFIX + customerId,
                Constants.LOCK_WAIT_IN_SECONDS,
                Constants.LOCK_LEASE_TIME))
        {
            throw new ResourceConflictException(Constants.ErrorMessage.UNFINISHED_TRANSACTION);
        }
    }

    private void releaseLock(String customerId){
        distributedLock.releaseLock(Constants.LOCK_CUSTOMER_PREFIX + customerId );
    }
}
