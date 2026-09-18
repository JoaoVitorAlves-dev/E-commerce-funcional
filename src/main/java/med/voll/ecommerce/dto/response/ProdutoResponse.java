package med.voll.ecommerce.dto.response;

import med.voll.ecommerce.entity.Produto;

public record ProdutoResponse(Long id, String nome, Double preco) {

    public ProdutoResponse(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getPreco());
    }

}
