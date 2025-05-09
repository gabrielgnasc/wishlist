package com.gabriel.wishlist.Domain.Entities;

import com.gabriel.wishlist.Common.Constants;
import com.gabriel.wishlist.Common.Exceptions.WishlistFullException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "wishlists")
public class Wishlist {
    @Id
    private String id;
    private String customerId;
    private Set<String> productIds = new HashSet<>();

    public Wishlist(String customerId){
        this.customerId = customerId;
    }

    public void addProduct(String productId) {
        if(productIds.size() >= Constants.MAX_LIMIT_WISHLIST){
            throw new WishlistFullException();
        }
        productIds.add(productId);
    }
}