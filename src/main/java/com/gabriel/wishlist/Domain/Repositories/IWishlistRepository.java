package com.gabriel.wishlist.Domain.Repositories;

import com.gabriel.wishlist.Domain.Entities.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface IWishlistRepository extends MongoRepository<Wishlist, String> {

    Optional<Wishlist> findByCustomerId(String costumerId);

    boolean existsByCustomerIdAndProductIds(String customerId, String productId);
}