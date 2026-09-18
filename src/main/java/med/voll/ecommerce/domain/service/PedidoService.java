package med.voll.ecommerce.domain.service;

import lombok.RequiredArgsConstructor;
import med.voll.ecommerce.domain.exceptions.IdNotFoundException;
import med.voll.ecommerce.domain.repository.ClienteRepository;
import med.voll.ecommerce.domain.repository.PedidoRepository;
import med.voll.ecommerce.dto.request.PedidoRequest;
import med.voll.ecommerce.dto.response.PedidoResponse;
import med.voll.ecommerce.entity.Cliente;
import med.voll.ecommerce.entity.Pedido;
import med.voll.ecommerce.mapper.PedidoMapper;
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
        return pedidoRepository.findByStatus(pageable)
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
        Pedido pedido = pedidoRepository.findById(pedidoRequest.clienteId()).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        pedido.setId(id);
        Pedido save = pedidoRepository.save(pedido);
        return PedidoMapper.toDTO(save);
    }

    public void deletarPorId(Long id) {
        pedidoRepository.deleteById(id);
    }

}
