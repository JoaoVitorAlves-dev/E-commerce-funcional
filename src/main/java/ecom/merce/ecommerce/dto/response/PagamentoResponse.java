package ecom.merce.ecommerce.dto.response;

import ecom.merce.ecommerce.domain.enums.StatusPagamento;

import java.time.LocalDateTime;

public record PagamentoResponse(Long id, Long pedidoId, String formaPagamento, Double valor, StatusPagamento statusPagamento, LocalDateTime dataPagamento) {
}
