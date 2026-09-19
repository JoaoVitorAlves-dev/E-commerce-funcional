package ecom.merce.ecommerce.dto.response;

import ecom.merce.ecommerce.entity.Endereco;

public record EnderecoResponse(Long id, Long clienteId, String rua, String cidade, String estado, String cep) {

    public EnderecoResponse(Endereco endereco) {
        this(endereco.getId(), endereco.getCliente().getId(), endereco.getRua(), endereco.getCidade(), endereco.getEstado(), endereco.getCep());
    }

}
