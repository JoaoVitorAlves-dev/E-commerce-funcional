package ecom.merce.ecommerce.domain.service;

import ecom.merce.ecommerce.domain.enums.StatusPedido;
import ecom.merce.ecommerce.domain.exceptions.IdNotFoundException;
import ecom.merce.ecommerce.domain.repository.ClienteRepository;
import ecom.merce.ecommerce.domain.repository.ItemPedidoRepository;
import ecom.merce.ecommerce.domain.repository.PagamentoRepository;
import ecom.merce.ecommerce.domain.repository.PedidoRepository;
import ecom.merce.ecommerce.dto.request.PedidoRequest;
import ecom.merce.ecommerce.dto.response.PedidoResponse;
import ecom.merce.ecommerce.entity.Cliente;
import ecom.merce.ecommerce.entity.Pedido;
import ecom.merce.ecommerce.mapper.PedidoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PagamentoRepository pagamentoRepository;

    private BigDecimal calcularTotal(Long pedidoId) {
        return itemPedidoRepository.findAllByPedidoId(pedidoId).stream()
                .map(item -> item.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void recalcularTotalPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        pedido.setTotal(calcularTotal(pedidoId));
        pedidoRepository.save(pedido);
    }

    public Page<PedidoResponse> listar(Pageable pageable, Authentication auth) {
        return pedidoRepository.findByClienteEmail(auth.getName(), pageable)
                .map(PedidoMapper::toDTO);
    }

    public PedidoResponse buscarPorId(Long id, Authentication auth) {
        Pedido pedido = pedidoRepository.findByIdAndClienteEmail(id, auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return PedidoMapper.toDTO(pedido);
    }

    public PedidoResponse adicionarPedido(PedidoRequest pedidoRequest, Authentication auth) {
        Cliente cliente = clienteRepository.findById(pedidoRequest.clienteId())
                .orElseThrow(() -> new IdNotFoundException("Cliente não existe."));

        if (!cliente.getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Pedido entity = PedidoMapper.toEntity(pedidoRequest, cliente);
        entity.setStatus(StatusPedido.ATIVO);
        entity.setTotal(BigDecimal.ZERO);

        return PedidoMapper.toDTO(pedidoRepository.save(entity));
    }

    public PedidoResponse atualizarPorId(Long id, PedidoRequest req, Authentication auth) {
        Pedido pedido = pedidoRepository.findByIdAndClienteEmail(id, auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        pedido.setTotal(calcularTotal(pedido.getId()));
        pedido.setDataPedido(req.dataPedido());

        return PedidoMapper.toDTO(pedidoRepository.save(pedido));
    }

    public void deletar(Long id, Authentication auth) {
        Pedido pedido = pedidoRepository.findByIdAndClienteEmail(id, auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (itemPedidoRepository.existsByPedidoId(id) || pagamentoRepository.existsByPedidoId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é possível excluir um pedido com itens ou pagamento. Cancele o pedido.");
        }

        pedidoRepository.delete(pedido);
    }

}
