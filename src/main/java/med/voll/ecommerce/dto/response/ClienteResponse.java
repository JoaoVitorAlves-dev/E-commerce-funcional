package med.voll.ecommerce.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteResponse(@NotNull Long id,
                              @NotBlank String nome,
                              @NotBlank @Email String email,
                              @NotBlank String senha,
                              @NotBlank String telefone) {
}
