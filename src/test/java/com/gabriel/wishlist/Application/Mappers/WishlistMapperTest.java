package com.gabriel.wishlist.Application.Mappers;

import com.gabriel.wishlist.Application.Models.WishlistDTO;
import com.gabriel.wishlist.Domain.Entities.Wishlist;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WishlistMapperTest {

    private final WishlistMapper wishlistMapper = new WishlistMapper();

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
        WishlistDTO wishlistDTO = wishlistMapper.ToWishlistDTO(wishlist);

        // Assert
        assertThat(wishlistDTO).isNotNull();
        assertThat(wishlistDTO.id()).isEqualTo(wishlistId);
        assertThat(wishlistDTO.customerId()).isEqualTo(customerId);
        assertThat(wishlistDTO.productIds()).isSameAs(productIds);
    }

    @Test
    void toWishlistDTO_ShouldHandleNullEntityGracefully() {
        // Arrange
        Wishlist wishlist = null;

        // Act
        WishlistDTO wishlistDTO = wishlistMapper.ToWishlistDTO(wishlist);

        // Assert
        assertThat(wishlistDTO).isNull();
    }
}
