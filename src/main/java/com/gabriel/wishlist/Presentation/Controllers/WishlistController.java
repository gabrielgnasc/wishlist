package com.gabriel.wishlist.Presentation.Controllers;

import com.gabriel.wishlist.Application.Interfaces.Services.IWishlistService;
import com.gabriel.wishlist.Application.Models.WishlistDTO;
import com.gabriel.wishlist.Presentation.Requests.AddProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final IWishlistService wishlistService;

    public WishlistController(IWishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }


    @PostMapping("/{customerId}/products")
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
            @PathVariable String productId
    ) {

        var response = wishlistService.removeProduct(customerId, productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{customerId}/products")
    public ResponseEntity<Set<String>> listProducts(
            @PathVariable String customerId
    ){

        var response = wishlistService.listProducts(customerId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<Boolean> containsProduct(
            @PathVariable String customerId,
            @RequestParam(name = "product_id") String productId
    ){
        var response = wishlistService.containsProduct(customerId, productId);
        return ResponseEntity.ok(response);
    }
}
