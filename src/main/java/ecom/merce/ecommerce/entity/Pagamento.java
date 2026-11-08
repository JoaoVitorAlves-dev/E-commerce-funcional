package ecom.merce.ecommerce.entity;

import ecom.merce.ecommerce.domain.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "forma_de_pagamento", nullable = false)
    private String formaDePagamento;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "status_pagamento", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    @Column(name = "data_pagamento", nullable = false)
    private LocalDateTime dataPagamento;
}
