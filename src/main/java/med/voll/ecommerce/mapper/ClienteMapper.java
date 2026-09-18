package med.voll.ecommerce.mapper;

import lombok.experimental.UtilityClass;
import med.voll.ecommerce.dto.request.ClienteRequest;
import med.voll.ecommerce.dto.response.ClienteResponse;
import med.voll.ecommerce.entity.Cliente;

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
                cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getSenha(), cliente.getTelefone()
        );
    }

}
