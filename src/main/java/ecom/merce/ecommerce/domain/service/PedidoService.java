package ecom.merce.ecommerce.domain.service;

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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public Page<PedidoResponse> listar(@PageableDefault(sort = "status") Pageable pageable) {
        return pedidoRepository.findAllBy(pageable)
                .map(PedidoResponse::new);
    }

    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        return PedidoMapper.toDTO(pedido);
    }

    public PedidoResponse adicionarPedido(PedidoRequest pedidoRequest) {
        Cliente cliente = clienteRepository.findById(pedidoRequest.clienteId()).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Pedido save = pedidoRepository.save(PedidoMapper.toEntity(pedidoRequest, cliente));
        return PedidoMapper.toDTO(save);
    }

    public PedidoResponse atualizarPorId(Long id, PedidoRequest pedidoRequest) {
        pedidoRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Cliente cliente = clienteRepository.findById(pedidoRequest.clienteId()).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Pedido pedido = PedidoMapper.toEntity(pedidoRequest, cliente);
        pedido.setId(id);
        Pedido save = pedidoRepository.save(pedido);
        return PedidoMapper.toDTO(save);
    }

    public void deletarPorId(Long id) {
        pedidoRepository.deleteById(id);
    }

}
