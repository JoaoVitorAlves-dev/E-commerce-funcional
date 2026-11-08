package ecom.merce.ecommerce.dto.response;

import jakarta.validation.constraints.NotBlank;

public record TokenResponse(
        @NotBlank String accessToken,
        @NotBlank String refreshToken
) {
}
