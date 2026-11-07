package ecom.merce.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoRequest(@NotNull Long categoriaId,
                             @NotBlank String nome,
                             @NotBlank String descricao,
                             @NotNull BigDecimal preco,
                             @NotNull Integer estoque) {
}
