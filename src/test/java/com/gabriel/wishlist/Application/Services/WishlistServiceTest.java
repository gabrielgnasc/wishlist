package com.gabriel.wishlist.Application.Services;

import com.gabriel.wishlist.Application.Interfaces.Mappers.IWishlistMapper;
import com.gabriel.wishlist.Common.Constants;
import com.gabriel.wishlist.Common.Exceptions.WishlistFullException;
import com.gabriel.wishlist.Common.Exceptions.WishlistNotFoundException;
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

    @Mock
    private IWishlistMapper mapper;

    @InjectMocks
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(mapper.ToWishlistDTO(any(Wishlist.class)))
                .thenAnswer(invocation -> WishlistHelper
                        .ToDTO(invocation.getArgument(0))
                );
    }

    @Test
    void addProduct_ShouldCreateWishlistIfDontFindByCostumer() {
        // Arrange
        String customerId = "customer1";
        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());
        when(wishlistRepository.save(any(Wishlist.class))).thenAnswer(invocation -> invocation.getArgument(0));
        // Act
        var wishlist = wishlistService.addProduct(customerId, "product21");

        //Assert
        assertThat(wishlist.productIds().size()).isEqualTo(1);
        assertThat(wishlist.customerId()).isEqualTo(customerId);
        Mockito.verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    void addProduct_ShouldThrowExceptionIfLimitExceeded() {
        // Arrange
        String customerId = "customer1";
        Wishlist wishlist = WishlistHelper.BuildWishlist(customerId);
        String errorMessage = Constants.ErrorMessage.WISHLIST_EXCEEDED_0_PRODUCTS
                .replace("{0}", String.valueOf(Constants.MAX_LIMIT_WISHLIST));

        for (int i = 0; i < 20; i++) {
            wishlist.addProduct("product" + i);
        }

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        // Act & Assert
        assertThatThrownBy(() -> wishlistService.addProduct(customerId, "product21"))
                .isInstanceOf(WishlistFullException.class)
                .hasMessage(errorMessage);

        Mockito.verify(wishlistRepository, never()).save(any(Wishlist.class));
    }

    @Test
    void addProduct_ShouldAddPProductInWishlist() {
        // Arrange
        int quantityProducts = 5;
        String customerId = "Customer1";
        var wishlist = WishlistHelper.BuildWishlistWithProducts(customerId, quantityProducts);

        when(wishlistRepository.findByCustomerId(customerId))
                .thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(any(Wishlist.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        // Act

        var response = wishlistService.addProduct(customerId, "product21");

        //Assert
        assertThat(response.productIds().size()).isEqualTo(quantityProducts + 1);
        assertThat(response.customerId()).isEqualTo(customerId);
        Mockito.verify(wishlistRepository, times(1))
                .save(any(Wishlist.class));
    }

    @Test
    void removeProduct_ShouldThrowExceptionIfCustomerDoesNotExist() {
        // Arrange
        String customerId = "customer1";
        String errorMessage = Constants.ErrorMessage.WISHLIST_OF_CUSTOMER_NOT_FOUND;

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> wishlistService.removeProduct(customerId, "product21"))
                .isInstanceOf(WishlistNotFoundException.class)
                .hasMessage(errorMessage);

        Mockito.verify(wishlistRepository, never())
                .save(any(Wishlist.class));
    }

    @Test
    void removeProduct_ShouldNotRemoveIfProductNotExisting() {
        // Arrange
        String customerId = "customer1";
        int initialQuantity = 5;
        Wishlist wishlist = WishlistHelper.BuildWishlistWithProducts(customerId, initialQuantity);

        when(wishlistRepository.findByCustomerId(customerId))
                .thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(any(Wishlist.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        var response = wishlistService.removeProduct(customerId, "product6");

        //Assert
        assertThat(response.productIds().size()).isEqualTo(initialQuantity);
        Mockito.verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    void removeProduct_ShouldRemoveIfProductExists() {
        // Arrange
        String customerId = "customer1";
        int initialQuantity = 5;
        Wishlist wishlist = WishlistHelper.BuildWishlistWithProducts(customerId, initialQuantity);

        when(wishlistRepository.findByCustomerId(customerId))
                .thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(any(Wishlist.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        var response = wishlistService.removeProduct(customerId, "product1");

        //Assert
        assertThat(response.productIds().size()).isEqualTo(initialQuantity - 1);
        Mockito.verify(wishlistRepository, times(1))
                .save(any(Wishlist.class));
    }

    @Test
    void listProducts_ShouldReturnEmptyListIfCustomerDoesNotExists(){
        // Arrange
        String customerId = "customer1";
        when(wishlistRepository.findByCustomerId(customerId))
                .thenReturn(Optional.empty());

        // Act
        var response = wishlistService.listProducts(customerId);

        //Assert
        assertThat(response.size()).isEqualTo(0);
    }

    @Test
    void listProducts_ShouldReturnAListOfProducts(){
        // Arrange
        String customerId = "customer1";
        int initialQuantity = 5;
        Wishlist wishlist = WishlistHelper.BuildWishlistWithProducts(customerId, initialQuantity);
        when(wishlistRepository.findByCustomerId(customerId))
                .thenReturn(Optional.of(wishlist));

        // Act
        var response = wishlistService.listProducts(customerId);

        //Assert
        assertThat(response.size()).isEqualTo(initialQuantity);
    }

    @Test
    void containsProduct_ShouldReturnTrueIfExists(){
        // Arrange
        String customerId = "customer1";
        String productId = "product1";

        when(wishlistRepository.existsByCustomerIdAndProductIds(customerId, productId))
                .thenReturn(true);

        // Act
        var response = wishlistService.containsProduct(customerId, productId);
        // Assert
        assertThat(response).isTrue();

    }

    @Test
    void containsProduct_ShouldReturnFalseIfNotExists(){
        // Arrange
        String customerId = "customer1";
        String productId = "product1";

        when(wishlistRepository.existsByCustomerIdAndProductIds(customerId, productId))
                .thenReturn(false);

        // Act
        var response = wishlistService.containsProduct(customerId, productId);
        // Assert
        assertThat(response).isFalse();

    }
}
