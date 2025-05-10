package com.gabriel.wishlist.Presentation.Controllers;

import com.gabriel.wishlist.Application.Interfaces.Services.IWishlistService;
import com.gabriel.wishlist.Application.Models.WishlistDTO;
import com.gabriel.wishlist.Presentation.Requests.AddProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final IWishlistService wishlistService;

    public WishlistController(IWishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }


    @PostMapping("/{customerId}")
    public ResponseEntity<WishlistDTO> addProduct(
            @PathVariable String customerId,
            @RequestBody AddProductRequest request
    ) {
        var response = wishlistService.addProduct(customerId, request.getProductId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{customerId}/products/{productId}")
    public ResponseEntity<WishlistDTO> removeProduct(
            @PathVariable String customerId,
            @PathVariable String productId) {

        var response = wishlistService.removeProduct(customerId, productId);
        return ResponseEntity.ok(response);
    }
}
