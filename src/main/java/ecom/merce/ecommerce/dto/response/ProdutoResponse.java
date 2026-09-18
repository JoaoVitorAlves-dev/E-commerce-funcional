package ecom.merce.ecommerce.dto.response;

import ecom.merce.ecommerce.entity.Produto;

public record ProdutoResponse(Long id, String nome, Double preco) {

    public ProdutoResponse(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getPreco());
    }

}
