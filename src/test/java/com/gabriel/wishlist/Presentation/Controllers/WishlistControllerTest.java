package com.gabriel.wishlist.Presentation.Controllers;

import com.gabriel.wishlist.Application.Interfaces.Services.IWishlistService;
import com.gabriel.wishlist.Domain.Entities.Wishlist;
import com.gabriel.wishlist.Presentation.Responses.WishlistResponse;

import com.gabriel.wishlist.Presentation.Requests.AddProductRequest;
import com.gabriel.wishlist.TestsHelpers.WishlistHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class WishlistControllerTest {

    @Mock
    private IWishlistService wishlistService;

    @InjectMocks
    private WishlistController wishlistController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProduct_ShouldReturnWishlistDTO() {
        // Arrange
        String customerId = "Customer1";
        String productId = "product1";
        Wishlist wishlist = WishlistHelper.BuildWishlistWithProducts(customerId, 3);

        when(wishlistService.addProduct(eq(customerId), eq(productId))).thenReturn(wishlist);

        // Act
        var response = wishlistController.addProduct(customerId, new AddProductRequest(productId));

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isInstanceOf(WishlistResponse.class);
        assertThat(response.getBody().customerId()).isEqualTo(customerId);
        assertThat(response.getBody().productIds()).contains(productId);

        verify(wishlistService, times(1)).addProduct(eq(customerId), eq(productId));
    }

    @Test
    void removeProduct_ShouldReturnWishlistDTO() {
        // Arrange
        String customerId = "Customer1";
        String productId = "product1";
        Wishlist wishlist = WishlistHelper.BuildWishlistWithProducts(customerId, 3);

        when(wishlistService.removeProduct(eq(customerId), eq(productId))).thenReturn(wishlist);

        // Act
        var response = wishlistController.removeProduct(customerId, productId);

        //Assert
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isInstanceOf(WishlistResponse.class);
        assertThat(response.getBody().customerId()).isEqualTo(customerId);
        assertThat(response.getBody().productIds()).contains(productId);

        verify(wishlistService, times(1)).removeProduct(eq(customerId), eq(productId));
    }

    @Test
    void listProducts_ShouldReturnAnProductList() {
        // Arrange
        String customerId = "Customer1";
        String productId = "Product1";

        when(wishlistService.listProducts(eq(customerId))).thenReturn(Set.of(productId));

        // Act
        var response = wishlistController.listProducts(customerId);

        //Assert
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isInstanceOf(Set.class);

        verify(wishlistService, times(1)).listProducts(eq(customerId));
    }

    @Test
    void productExist_ShouldReturnResponse() {
        // Arrange
        String customerId = "Customer1";
        String productId = "Product1";

        when(wishlistService.containsProduct(eq(customerId), eq(productId))).thenReturn(true);

        // Act
        var response = wishlistController.containsProduct(customerId, productId);

        //Assert
        assertThat(response).isNotNull();
        Assertions.assertTrue(response.getBody().hasProduct());
        assertThat(response.getBody().productId()).isEqualTo(productId);
        verify(wishlistService, times(1)).containsProduct(eq(customerId), eq(productId));
    }
}
