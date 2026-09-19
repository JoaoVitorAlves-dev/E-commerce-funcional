package ecom.merce.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EnderecoRequest(@NotBlank String rua,
                              @NotBlank String cidade,
                              @NotBlank String estado,
                              @NotBlank String cep) {
}
