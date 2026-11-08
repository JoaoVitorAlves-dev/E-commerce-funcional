package ecom.merce.ecommerce.domain.repository;

import ecom.merce.ecommerce.entity.Cliente;
import ecom.merce.ecommerce.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    void deleteByCliente(Cliente cliente);

    Optional<RefreshToken> findByToken(String token);

}
