package ecom.merce.ecommerce.mapper;

import ecom.merce.ecommerce.dto.request.ProdutoRequest;
import ecom.merce.ecommerce.dto.response.ProdutoResponse;
import ecom.merce.ecommerce.entity.Produto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProdutoMapper {

    public Produto toEntity(ProdutoRequest produtoRequest) {
        Produto produto = new Produto();
        produto.setNome(produtoRequest.nome());
        produto.setPreco(produtoRequest.preco());
        return produto;
    }

    public ProdutoResponse toDTO(Produto produto) {
        return new ProdutoResponse(
                produto.getId(), produto.getNome(), produto.getPreco()
        );
    }

}
