package com.gabriel.wishlist.Application.Services;

import com.gabriel.wishlist.Domain.Entities.Wishlist;
import com.gabriel.wishlist.Domain.Repositories.IWishlistRepository;
import com.gabriel.wishlist.TestsHelpers.WishlistHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class WishlistServiceTest {

    @Mock
    private IWishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProduct_ShouldCreateWishlistIfDontFindByCostumer() {
        // Arrange
        String customerId = "customer1";
        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

        // Act
        var wishlist = wishlistService.addProduct(customerId, "product21");

        //Assert
        assertThat(wishlist.getProductIds().size()).isEqualTo(1);
        Mockito.verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    void addProduct_ShouldThrowExceptionIfLimitExceeded() {
        // Arrange
        String customerId = "customer1";
        Wishlist wishlist = WishlistHelper.BuildWishlist(customerId);

        for (int i = 0; i < 20; i++) {
            wishlist.addProduct("product" + i);
        }

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        // Act & Assert
        assertThatThrownBy(() -> wishlistService.addProduct(customerId, "product21"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Wishlist cannot contain more than 20 products.");
        Mockito.verify(wishlistRepository, never()).save(any(Wishlist.class));
    }
}
