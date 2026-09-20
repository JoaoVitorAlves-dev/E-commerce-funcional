package ecom.merce.ecommerce.dto.response;

import ecom.merce.ecommerce.entity.Cliente;

public record ClienteResponse(Long id, String nome, String email, String telefone) {

    public ClienteResponse(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getTelefone());
    }


}
