package med.voll.ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequest(@NotBlank String nome,
                             @NotBlank @Email String email,
                             @NotBlank String senha,
                             @NotBlank String telefone) {
}
