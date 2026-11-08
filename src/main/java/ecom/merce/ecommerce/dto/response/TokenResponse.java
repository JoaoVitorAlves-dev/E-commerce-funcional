package ecom.merce.ecommerce.dto.response;

public record TokenResponse(
        String accessToken, String refreshToken
) {
}
