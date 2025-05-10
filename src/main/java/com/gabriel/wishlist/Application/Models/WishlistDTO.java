package com.gabriel.wishlist.Application.Models;

import lombok.Builder;

import java.util.Set;

public record WishlistDTO(
        String id,
        String customerId,
        Set<String> productIds
) {
}
