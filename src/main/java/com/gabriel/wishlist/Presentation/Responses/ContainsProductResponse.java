package com.gabriel.wishlist.Presentation.Responses;

public record ContainsProductResponse(
        boolean hasProduct,
        String productId
)
{
}
