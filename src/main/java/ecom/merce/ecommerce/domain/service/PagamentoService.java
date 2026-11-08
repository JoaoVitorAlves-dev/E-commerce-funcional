package ecom.merce.ecommerce.domain.service;

import ecom.merce.ecommerce.domain.enums.StatusPagamento;
import ecom.merce.ecommerce.domain.exceptions.IdNotFoundException;
import ecom.merce.ecommerce.domain.repository.PagamentoRepository;
import ecom.merce.ecommerce.domain.repository.PedidoRepository;
import ecom.merce.ecommerce.dto.request.PagamentoRequest;
import ecom.merce.ecommerce.dto.response.PagamentoResponse;
import ecom.merce.ecommerce.entity.Pagamento;
import ecom.merce.ecommerce.entity.Pedido;
import ecom.merce.ecommerce.mapper.PagamentoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;

    public Page<PagamentoResponse> listar(Authentication auth, Pageable pageable) {
        return pagamentoRepository.findByPedidoClienteEmail(auth.getName(), pageable).map(PagamentoMapper::toDTO);
    }

    public PagamentoResponse buscarPorId(Long id, Authentication auth) {
        Pagamento pagamento = pagamentoRepository.findByIdAndPedidoClienteEmail(id, auth.getName())
                .orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        return PagamentoMapper.toDTO(pagamento);
    }

    public PagamentoResponse adicionarPagamento(PagamentoRequest req) {
        Pedido pedido = pedidoRepository.findById(req.pedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Pagamento entity = PagamentoMapper.toEntity(req);
        entity.setPedido(pedido);

        return PagamentoMapper.toDTO(pagamentoRepository.save(entity));
    }

    public PagamentoResponse atualizarPorId(Long id, PagamentoRequest req, Authentication auth) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("ID Não existe"));

        if (!pagamento.getPedido().getCliente().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Pedido pedido = pedidoRepository.findById(req.pedidoId())
                .orElseThrow(() -> new IdNotFoundException("ID Não existe"));

        if (!pedido.getCliente().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setFormaDePagamento(req.formaPagamento());
        pagamento.setPedido(pedido);
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);

        return PagamentoMapper.toDTO(pagamentoRepository.save(pagamento));
    }

    public void deletar(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        pagamentoRepository.delete(pagamento);
    }

}
