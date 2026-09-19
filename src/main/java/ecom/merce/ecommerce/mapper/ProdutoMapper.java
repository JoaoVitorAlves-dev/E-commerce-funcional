package ecom.merce.ecommerce.mapper;

import ecom.merce.ecommerce.dto.request.ProdutoRequest;
import ecom.merce.ecommerce.dto.response.ProdutoResponse;
import ecom.merce.ecommerce.entity.Categoria;
import ecom.merce.ecommerce.entity.Produto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProdutoMapper {

    public Produto toEntity(ProdutoRequest produtoRequest, Categoria categoria) {
        Produto produto = new Produto();
        produto.setCategoria(categoria);
        produto.setNome(produtoRequest.nome());
        produto.setDescricao(produtoRequest.descricao());
        produto.setPreco(produtoRequest.preco());
        produto.setEstoque(produtoRequest.estoque());
        return produto;
    }

    public ProdutoResponse toDTO(Produto produto) {
        return new ProdutoResponse(
                produto.getId(), produto.getCategoria().getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getEstoque()
        );
    }

}
