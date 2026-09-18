package ecom.merce.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoRequest(@NotBlank String nome,
                             @NotNull Double preco) {
}
