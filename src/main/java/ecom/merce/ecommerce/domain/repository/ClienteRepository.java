package ecom.merce.ecommerce.domain.repository;

import ecom.merce.ecommerce.entity.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Page<Cliente> findAllBy(Pageable pageable);
    Optional<Cliente> findByEmail(String email);

    boolean existsByEmail(@NotBlank @Email String email);
}
