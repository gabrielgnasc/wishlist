package com.gabriel.wishlist.Domain.Entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "wishlists")
public class Wishlist {
    @Id
    private String id;
    private String customerId;
    private Set<String> productIds = new HashSet<>();
}