package ecom.merce.ecommerce.mapper;

import ecom.merce.ecommerce.dto.request.CategoriaRequest;
import ecom.merce.ecommerce.dto.response.CategoriaResponse;
import ecom.merce.ecommerce.entity.Categoria;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoriaMapper {

    public Categoria toEntity(CategoriaRequest categoriaRequest) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaRequest.nome());
        return categoria;
    }

    public CategoriaResponse toDTO(Categoria categoria) {
        return new CategoriaResponse(
          categoria.getId(), categoria.getNome()
        );
    }

}
