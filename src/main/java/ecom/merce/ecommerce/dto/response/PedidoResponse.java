package ecom.merce.ecommerce.dto.response;

import ecom.merce.ecommerce.domain.enums.StatusPedido;
import ecom.merce.ecommerce.entity.Pedido;

import java.time.LocalDate;

public record PedidoResponse(Long id, Long clienteId, LocalDate dataPedido, StatusPedido status, Double total) {

    public PedidoResponse(Pedido pedido) {
        this(pedido.getId(), pedido.getCliente().getId(), pedido.getDataPedido(), pedido.getStatus(), pedido.getTotal());
    }

}
