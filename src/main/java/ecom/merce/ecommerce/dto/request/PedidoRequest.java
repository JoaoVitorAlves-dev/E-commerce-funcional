package ecom.merce.ecommerce.dto.request;

import ecom.merce.ecommerce.domain.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PedidoRequest(@NotNull Long clienteId,
                            @NotNull LocalDate dataPedido,
                            @NotNull StatusPedido status,
                            @NotNull Double total) {
}
