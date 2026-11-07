package ecom.merce.ecommerce.dto.response;

import ecom.merce.ecommerce.entity.ItemPedido;

import java.math.BigDecimal;

public record ItemPedidoResponse(Long id, Long pedidoId, Long produtoId, Integer quantidade, BigDecimal precoUnitario) {

    public ItemPedidoResponse(ItemPedido itemPedido) {
        this(itemPedido.getId(), itemPedido.getPedido().getId(), itemPedido.getProduto().getId(), itemPedido.getQuantidade(), itemPedido.getPrecoUnitario());
    }

}
