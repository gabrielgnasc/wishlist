package com.gabriel.wishlist.Presentation.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "AddProductRequest", description = "Requisição para adicionar produto à wishlist")
public class AddProductRequest {

    @NotBlank
    @Schema(description = "ID do produto a ser adicionado", example = "67890")
    private String productId;
}