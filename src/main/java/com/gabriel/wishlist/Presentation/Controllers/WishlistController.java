package com.gabriel.wishlist.Presentation.Controllers;

import com.gabriel.wishlist.Application.Interfaces.Services.IWishlistService;
import com.gabriel.wishlist.Application.Models.WishlistDTO;
import com.gabriel.wishlist.Presentation.Requests.AddProductRequest;
import com.gabriel.wishlist.Presentation.Responses.ContainsProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/wishlist")
@Validated
@Tag(name = "Lista de Desejos (Wishlist)", description = "Endpoints para gerenciar produtos na lista de desejos")
public class WishlistController {

    private final IWishlistService wishlistService;

    public WishlistController(IWishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @Operation(summary= "Adicionar produto",
            description= "Adiciona um produto da Wishlist, se não existir, cria uma nova com o produto enviado")
    @ApiResponse(responseCode = "200",description = "retorna Wishlist")
    @ApiResponse(responseCode = "400", description = "A lista de desejos já atingiu seu limite máximo.")
    @PostMapping("/{customerId}/products")
    public ResponseEntity<WishlistDTO> addProduct(
            @PathVariable String customerId,
            @RequestBody AddProductRequest request
    ) {
        var response = wishlistService.addProduct(customerId, request.getProductId());
        return ResponseEntity.ok(response);
    }

    @Operation(summary= "Remover um produto",description= "Remove um produto na lista da Wishlist")
    @ApiResponse(responseCode= "200",description= "retorna Wishlist atualizada")
    @ApiResponse(responseCode= "404", description= "A lista de desejos do cliente solicitado não foi encontrada.")
    @DeleteMapping("/{customerId}/products/{productId}")
    public ResponseEntity<WishlistDTO> removeProduct(
            @PathVariable String customerId,
            @PathVariable String productId
    ) {

        var response = wishlistService.removeProduct(customerId, productId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar produtos", description= "Lista os produtos da Wishlist")
    @ApiResponse(responseCode = "200", description= "retorna produtos da Wishlist")
    @ApiResponse(responseCode = "404", description= "A lista de desejos do cliente solicitado não foi encontrada.")
    @GetMapping("/{customerId}/products")
    public ResponseEntity<Set<String>> listProducts(
            @PathVariable String customerId
    ){

        var response = wishlistService.listProducts(customerId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Contém produto", description= "Verifica se contém um produto na Wishlist")
    @ApiResponse(responseCode = "200", description= "retorna se produto existe na Wishlist")
    @GetMapping("/{customerId}")
    public ResponseEntity<ContainsProductResponse> containsProduct(
            @PathVariable String customerId,
            @RequestParam(name = "product_id") String productId
    ){
        var response = wishlistService.containsProduct(customerId, productId);
        var dto = new ContainsProductResponse(response, productId);
        return ResponseEntity.ok(dto);
    }
}
