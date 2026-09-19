package ecom.merce.ecommerce.mapper;

import ecom.merce.ecommerce.dto.request.EnderecoRequest;
import ecom.merce.ecommerce.dto.response.EnderecoResponse;
import ecom.merce.ecommerce.entity.Endereco;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EnderecoMapper {

    public Endereco toEntity(EnderecoRequest enderecoRequest) {
        Endereco endereco = new Endereco();
        endereco.setRua(enderecoRequest.rua());
        endereco.setCidade(enderecoRequest.cidade());
        endereco.setEstado(enderecoRequest.estado());
        endereco.setCep(enderecoRequest.cep());
        return endereco;
    }

    public EnderecoResponse toDTO(Endereco endereco) {
        return new EnderecoResponse(
                endereco.getId(), endereco.getCliente().getId(), endereco.getRua(), endereco.getCidade(), endereco.getEstado(), endereco.getCep()
        );
    }

}
