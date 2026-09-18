package med.voll.ecommerce.dto.response;

import med.voll.ecommerce.entity.ItemPedido;

public record ItemPedidoResponse(Long id, Long pedidoId, Long produtoId, Integer quantidade, Integer precoUnitario) {

    public ItemPedidoResponse(ItemPedido itemPedido) {
        this(itemPedido.getId(), itemPedido.getId(), itemPedido.getProduto().getId(), itemPedido.getQuantidade(), itemPedido.getPrecoUnitario());
    }

}
