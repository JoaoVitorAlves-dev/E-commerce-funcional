package ecom.merce.ecommerce.domain.repository;

import ecom.merce.ecommerce.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Page<Pedido> findByClienteEmail(String email, Pageable pageable);

    Optional<Pedido> findByClienteEmail(String email);
}
