package ecom.merce.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;

public record ItemPedidoRequest(@NotNull Long pedidoId,
                         @NotNull Long produtoId,
                         @NotNull Integer quantidade,
                         @NotNull Integer precoUnitario) {
}
