package ecom.merce.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoRequest(@NotNull Long clienteId,
                              @NotBlank String rua,
                              @NotBlank String cidade,
                              @NotBlank String estado,
                              @NotBlank String cep) {
}
