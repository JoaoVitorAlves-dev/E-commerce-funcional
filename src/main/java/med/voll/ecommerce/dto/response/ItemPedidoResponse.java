package med.voll.ecommerce.dto.response;

import jakarta.validation.constraints.NotNull;

public record ItemPedidoResponse(@NotNull Long id,
                                 @NotNull Long pedidoId,
                                 @NotNull Long produtoId,
                                 @NotNull Integer quantidade,
                                 @NotNull Integer precoUnitario) {
}
