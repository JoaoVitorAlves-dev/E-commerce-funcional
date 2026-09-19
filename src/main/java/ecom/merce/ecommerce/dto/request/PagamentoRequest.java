package ecom.merce.ecommerce.dto.request;

import ecom.merce.ecommerce.domain.enums.StatusPagamento;

import java.time.LocalDateTime;

public record PagamentoRequest(Long pedidoId, String formaPagamento, Double valor, StatusPagamento statusPagamento, LocalDateTime dataPagamento) {
}
