package ecom.merce.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemPedidoRequest(@NotNull Long pedidoId,
                         @NotNull Long produtoId,
                         @NotNull Integer quantidade,
                         @NotNull BigDecimal precoUnitario) {
}
