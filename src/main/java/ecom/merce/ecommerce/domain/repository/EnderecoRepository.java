package ecom.merce.ecommerce.domain.repository;

import ecom.merce.ecommerce.entity.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Page<Endereco> findAllBy(Pageable pageable);

    Optional<Endereco> findByClienteEmail(String email);

}
