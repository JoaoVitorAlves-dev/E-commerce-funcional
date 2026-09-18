package ecom.merce.ecommerce.domain.repository;

import ecom.merce.ecommerce.entity.ItemPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    Page<ItemPedido> findByQuantidade(Pageable pageable);
}
