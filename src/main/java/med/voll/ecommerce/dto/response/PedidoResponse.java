package med.voll.ecommerce.dto.response;

import med.voll.ecommerce.domain.enums.StatusPedido;
import med.voll.ecommerce.entity.Pedido;

import java.time.LocalDate;

public record PedidoResponse(Long id, Long clienteId, LocalDate dataPedido, StatusPedido status, Double total) {

    public PedidoResponse(Pedido pedido) {
        this(pedido.getId(), pedido.getCliente().getId(), pedido.getDataPedido(), pedido.getStatus(), pedido.getTotal());
    }

}
