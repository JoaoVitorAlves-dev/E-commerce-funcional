package med.voll.ecommerce.dto.response;

import jakarta.validation.constraints.NotNull;
import med.voll.ecommerce.domain.StatusPedido;

import java.time.LocalDate;

public record PedidoResponse(@NotNull Long id,
                             @NotNull Long clienteId,
                             @NotNull LocalDate dataPedido,
                             @NotNull StatusPedido status,
                             @NotNull Double total) {
}
