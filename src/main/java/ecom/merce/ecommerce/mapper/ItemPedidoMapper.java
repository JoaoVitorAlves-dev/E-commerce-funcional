package ecom.merce.ecommerce.mapper;

import ecom.merce.ecommerce.dto.request.ItemPedidoRequest;
import ecom.merce.ecommerce.dto.response.ItemPedidoResponse;
import ecom.merce.ecommerce.entity.ItemPedido;
import ecom.merce.ecommerce.entity.Pedido;
import ecom.merce.ecommerce.entity.Produto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemPedidoMapper {

    public ItemPedido toEntity(ItemPedidoRequest itemPedidoRequest, Pedido pedido, Produto produto) {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(itemPedidoRequest.quantidade());
        itemPedido.setPrecoUnitario(itemPedidoRequest.precoUnitario());
        return itemPedido;
    }

    public ItemPedidoResponse toDTO(ItemPedido itemPedido) {
        return new ItemPedidoResponse(
                itemPedido.getId(), itemPedido.getPedido().getId(), itemPedido.getProduto().getId(), itemPedido.getQuantidade(), itemPedido.getPrecoUnitario()
        );
    }

}
