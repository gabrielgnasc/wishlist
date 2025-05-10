package com.gabriel.wishlist.Presentation.Requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddProductRequest {

    @NotBlank
    private String productId;
}