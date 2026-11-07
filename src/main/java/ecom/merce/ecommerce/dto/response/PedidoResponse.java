package ecom.merce.ecommerce.dto.response;

import ecom.merce.ecommerce.domain.enums.StatusPedido;
import ecom.merce.ecommerce.entity.Pedido;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PedidoResponse(Long id, Long clienteId, LocalDate dataPedido, StatusPedido status, BigDecimal total) {

    public PedidoResponse(Pedido pedido) {
        this(pedido.getId(), pedido.getCliente().getId(), pedido.getDataPedido(), pedido.getStatus(), pedido.getTotal());
    }

}
