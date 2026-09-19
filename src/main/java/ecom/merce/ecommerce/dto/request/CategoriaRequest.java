package ecom.merce.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequest(@NotBlank String nome) {
}
