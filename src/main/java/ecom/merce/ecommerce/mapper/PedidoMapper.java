package ecom.merce.ecommerce.mapper;

import ecom.merce.ecommerce.dto.request.PedidoRequest;
import ecom.merce.ecommerce.dto.response.PedidoResponse;
import ecom.merce.ecommerce.entity.Cliente;
import ecom.merce.ecommerce.entity.Pedido;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PedidoMapper {

    public Pedido toEntity(PedidoRequest pedidoRequest, Cliente cliente) {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(pedidoRequest.dataPedido());
        pedido.setStatus(pedidoRequest.status());
        pedido.setTotal(pedidoRequest.total());
        return pedido;
    }

    public PedidoResponse toDTO(Pedido pedido) {
        return new PedidoResponse(
          pedido.getId(), pedido.getCliente().getId(), pedido.getDataPedido(), pedido.getStatus(), pedido.getTotal()
        );
    }

}
