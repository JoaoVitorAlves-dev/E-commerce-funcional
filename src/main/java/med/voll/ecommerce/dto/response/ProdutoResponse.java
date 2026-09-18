package med.voll.ecommerce.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoResponse(@NotNull Long id,
                              @NotBlank String nome,
                              @NotNull Double preco) {
}
