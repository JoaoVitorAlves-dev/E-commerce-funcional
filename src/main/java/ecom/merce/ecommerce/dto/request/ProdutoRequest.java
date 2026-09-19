package ecom.merce.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoRequest(@NotNull Long categoriaId,
                             @NotBlank String nome,
                             @NotBlank String descricao,
                             @NotNull Double preco,
                             @NotNull Integer estoque) {
}
