package med.voll.ecommerce.mapper;

import lombok.experimental.UtilityClass;
import med.voll.ecommerce.dto.request.ProdutoRequest;
import med.voll.ecommerce.dto.response.ProdutoResponse;
import med.voll.ecommerce.entity.Produto;

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
