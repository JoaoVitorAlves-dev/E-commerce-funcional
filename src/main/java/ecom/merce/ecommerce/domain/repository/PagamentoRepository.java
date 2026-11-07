package ecom.merce.ecommerce.domain.repository;

import ecom.merce.ecommerce.entity.Pagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Page<Pagamento> findByPedidoClienteEmail(String email, Pageable pageable);

    Optional<Pagamento> findByIdAndPedidoClienteEmail(Long id, String name);
}
