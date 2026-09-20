package ecom.merce.ecommerce.mapper;

import ecom.merce.ecommerce.dto.request.ClienteRequest;
import ecom.merce.ecommerce.dto.response.ClienteResponse;
import ecom.merce.ecommerce.entity.Cliente;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClienteMapper {

    public Cliente toEntity(ClienteRequest clienteRequest) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteRequest.nome());
        cliente.setEmail(clienteRequest.email());
        cliente.setSenha(clienteRequest.senha());
        cliente.setTelefone(clienteRequest.telefone());
        return cliente;
    }

    public ClienteResponse toDTO(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getTelefone()
        );
    }

}
