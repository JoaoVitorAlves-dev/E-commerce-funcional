package ecom.merce.ecommerce.domain.service;

import ecom.merce.ecommerce.domain.enums.StatusPedido;
import ecom.merce.ecommerce.domain.exceptions.IdNotFoundException;
import ecom.merce.ecommerce.domain.repository.ClienteRepository;
import ecom.merce.ecommerce.domain.repository.PedidoRepository;
import ecom.merce.ecommerce.dto.request.PedidoRequest;
import ecom.merce.ecommerce.dto.response.PedidoResponse;
import ecom.merce.ecommerce.entity.Cliente;
import ecom.merce.ecommerce.entity.Pedido;
import ecom.merce.ecommerce.mapper.PedidoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public Page<PedidoResponse> listar(Pageable pageable, Authentication auth) {
        return pedidoRepository.findByClienteEmail(auth.getName(), pageable).map(PedidoMapper::toDTO);
    }

    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        return PedidoMapper.toDTO(pedido);
    }

    public PedidoResponse adicionarPedido(PedidoRequest pedidoRequest, Authentication auth) {
        Cliente cliente = clienteRepository.findById(pedidoRequest.clienteId())
                .orElseThrow(() -> new IdNotFoundException("Cliente não existe."));

        if (!cliente.getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Pedido entity = PedidoMapper.toEntity(pedidoRequest, cliente);

        if (entity.getStatus().equals(StatusPedido.CANCELADO)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return PedidoMapper.toDTO(pedidoRepository.save(entity));
    }

    public PedidoResponse atualizarPorId(Long id, PedidoRequest req, Authentication auth) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Pedido não existe."));

        Cliente cliente = clienteRepository.findById(req.clienteId())
                .orElseThrow(() -> new IdNotFoundException("Cliente não existe."));

        Pedido entity = PedidoMapper.toEntity(req, cliente);

        if (!entity.getCliente().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if (!pedido.getCliente().getEmail().equals(auth.getName()) || req.status().equals(StatusPedido.CANCELADO)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        pedido.setCliente(cliente);
        pedido.setDataPedido(req.dataPedido());
        pedido.setStatus(req.status());
        pedido.setTotal(req.total());

        return PedidoMapper.toDTO(pedidoRepository.save(pedido));
    }

    public void deletar(Authentication auth) {
        Pedido pedido = pedidoRepository.findByClienteEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        pedidoRepository.delete(pedido);
    }

}
