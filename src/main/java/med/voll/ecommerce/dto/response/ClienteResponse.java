package med.voll.ecommerce.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.ecommerce.entity.Cliente;
import org.springframework.lang.Contract;

public record ClienteResponse(Long id, String nome, String email, String senha, String telefone) {

    public ClienteResponse(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getSenha(), cliente.getTelefone());
    }


}
