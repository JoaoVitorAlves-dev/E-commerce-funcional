package ecom.merce.ecommerce.domain.repository;

import ecom.merce.ecommerce.entity.ItemPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    List<ItemPedido> findAllByPedidoId(Long pedidoId);

    Page<ItemPedido> findByPedidoClienteEmail(String email, Pageable pageable);

    Optional<ItemPedido> findByIdAndPedidoClienteEmail(Long id, String email);

    boolean existsByPedidoId(Long id);

    boolean existsByProdutoId(Long id);
}
