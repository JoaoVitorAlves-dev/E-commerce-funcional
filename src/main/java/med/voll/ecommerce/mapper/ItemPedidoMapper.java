package med.voll.ecommerce.mapper;

import lombok.experimental.UtilityClass;
import med.voll.ecommerce.dto.request.ItemPedidoRequest;
import med.voll.ecommerce.dto.response.ItemPedidoResponse;
import med.voll.ecommerce.entity.ItemPedido;
import med.voll.ecommerce.entity.Pedido;
import med.voll.ecommerce.entity.Produto;

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
