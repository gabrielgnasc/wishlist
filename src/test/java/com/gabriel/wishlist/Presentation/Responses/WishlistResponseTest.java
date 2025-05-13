package com.gabriel.wishlist.Presentation.Responses;

import com.gabriel.wishlist.Domain.Entities.Wishlist;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WishlistResponseTest {


    @Test
    void toWishlistDTO_ShouldMapEntityToDTO() {
        // Arrange
        String wishlistId = "wishlist1";
        String customerId = "customer1";
        Set<String> productIds = Set.of("product1", "product2", "product3");
        Wishlist wishlist = new Wishlist();
        wishlist.setId(wishlistId);
        wishlist.setCustomerId(customerId);
        wishlist.setProductIds(productIds);

        // Act
        WishlistResponse wishlistResponse = WishlistResponse.fromEntity(wishlist);

        // Assert
        assertThat(wishlistResponse).isNotNull();
        assertThat(wishlistResponse.id()).isEqualTo(wishlistId);
        assertThat(wishlistResponse.customerId()).isEqualTo(customerId);
        assertThat(wishlistResponse.productIds()).isSameAs(productIds);
    }

    @Test
    void toWishlistDTO_ShouldHandleNullEntityGracefully() {
        // Arrange
        Wishlist wishlist = null;

        // Act
        WishlistResponse wishlistResponse = WishlistResponse.fromEntity(wishlist);

        // Assert
        assertThat(wishlistResponse).isNull();
    }
}
