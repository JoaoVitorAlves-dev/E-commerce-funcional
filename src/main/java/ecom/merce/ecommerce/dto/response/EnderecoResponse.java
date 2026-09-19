package ecom.merce.ecommerce.dto.response;

public record EnderecoResponse(Long id, Long clienteId, String rua, String cidade, String estado, String cep) {
}
