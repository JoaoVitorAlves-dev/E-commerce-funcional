package ecom.merce.ecommerce.dto.response;

import ecom.merce.ecommerce.entity.Produto;

public record ProdutoResponse(Long id, Long categoriaId, String nome, String descricao, Double preco, Integer estoque) {

    public ProdutoResponse(Produto produto) {
        this(produto.getId(), produto.getCategoria().getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getEstoque());
    }

}
