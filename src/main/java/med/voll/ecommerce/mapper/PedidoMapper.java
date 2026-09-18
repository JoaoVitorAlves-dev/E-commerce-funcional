package med.voll.ecommerce.mapper;

import lombok.experimental.UtilityClass;
import med.voll.ecommerce.dto.request.PedidoRequest;
import med.voll.ecommerce.dto.response.PedidoResponse;
import med.voll.ecommerce.entity.Cliente;
import med.voll.ecommerce.entity.Pedido;

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
