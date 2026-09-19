package ecom.merce.ecommerce.dto.request;

import ecom.merce.ecommerce.domain.enums.StatusPagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PagamentoRequest(@NotNull Long pedidoId,
                               @NotBlank String formaPagamento,
                               @NotNull Double valor,
                               @NotNull StatusPagamento statusPagamento,
                               @NotNull LocalDateTime dataPagamento) {
}
