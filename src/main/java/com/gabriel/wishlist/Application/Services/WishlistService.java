package com.gabriel.wishlist.Application.Services;

import com.gabriel.wishlist.Application.Interfaces.ExternalServices.IDistributedLock;
import com.gabriel.wishlist.Application.Interfaces.Services.IWishlistService;
import com.gabriel.wishlist.Common.Constants;
import com.gabriel.wishlist.Common.Exceptions.ResourceConflictException;
import com.gabriel.wishlist.Common.Exceptions.WishlistNotFoundException;
import com.gabriel.wishlist.Domain.Entities.Wishlist;
import com.gabriel.wishlist.Domain.Repositories.IWishlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WishlistService implements IWishlistService {

    private final Logger logger = LoggerFactory.getLogger(WishlistService.class);
    private final IWishlistRepository wishlistRepository;
    private final IDistributedLock distributedLock;

    public WishlistService(IWishlistRepository wishlistRepository, IDistributedLock distributedLock) {
        this.wishlistRepository = wishlistRepository;
        this.distributedLock = distributedLock;
    }


    @Override
    public Wishlist addProduct(String customerId, String productId) {
        acquireLock(customerId);
        try{

            Wishlist wishlist = wishlistRepository
                    .findByCustomerId(customerId)
                    .orElse(new Wishlist(customerId));

            wishlist.addProduct(productId);
            logger.info("Produto {} adicionado na wishlist do cliente {}", productId, customerId);

            return wishlistRepository.save(wishlist);
        }finally {
            releaseLock(customerId);
        }
    }

    @Override
    public Wishlist removeProduct(String customerId, String productId) {
        acquireLock(customerId);
        try{

            Wishlist wishlist = wishlistRepository
                    .findByCustomerId(customerId)
                    .orElseThrow(WishlistNotFoundException::new);

            wishlist.removeProduct(productId);
            logger.info("Produto {} removido da wishlist do cliente {}", productId, customerId);

            return wishlistRepository.save(wishlist);
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
        logger.info("Lock adquirido com sucesso. Cliente: {}", customerId);
    }

    private void releaseLock(String customerId){
        distributedLock.releaseLock(Constants.LOCK_CUSTOMER_PREFIX + customerId );
        logger.info("Lock liberado com sucesso. Cliente: {}", customerId);
    }
}
