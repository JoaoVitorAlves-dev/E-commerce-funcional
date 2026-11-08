package ecom.merce.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PagamentoRequest(@NotNull Long pedidoId,
                               @NotBlank String formaPagamento) {
}
