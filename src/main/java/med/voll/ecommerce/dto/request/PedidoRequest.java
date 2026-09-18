package med.voll.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import med.voll.ecommerce.domain.enums.StatusPedido;

import java.time.LocalDate;

public record PedidoRequest(@NotNull Long clienteId,
                            @NotNull LocalDate dataPedido,
                            @NotNull StatusPedido status,
                            @NotNull Double total) {
}
