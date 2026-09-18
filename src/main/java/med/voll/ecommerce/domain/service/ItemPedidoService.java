package med.voll.ecommerce.domain.service;

import lombok.RequiredArgsConstructor;
import med.voll.ecommerce.domain.exceptions.IdNotFoundException;
import med.voll.ecommerce.domain.repository.ItemPedidoRepository;
import med.voll.ecommerce.domain.repository.PedidoRepository;
import med.voll.ecommerce.domain.repository.ProdutoRepository;
import med.voll.ecommerce.dto.request.ItemPedidoRequest;
import med.voll.ecommerce.dto.response.ItemPedidoResponse;
import med.voll.ecommerce.entity.ItemPedido;
import med.voll.ecommerce.entity.Pedido;
import med.voll.ecommerce.entity.Produto;
import med.voll.ecommerce.mapper.ItemPedidoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public Page<ItemPedidoResponse> listar(@PageableDefault(sort = "quantidade") Pageable pageable) {
        return itemPedidoRepository.findByQuantidade(pageable).map(ItemPedidoResponse::new);
    }

    public ItemPedidoResponse buscarPorId(Long id) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID não existe"));
        return ItemPedidoMapper.toDTO(itemPedido);
    }

    public ItemPedidoResponse adicionarItemPedido(ItemPedidoRequest itemPedidoRequest) {
        Pedido pedido = pedidoRepository.findById(itemPedidoRequest.pedidoId()).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Produto produto = produtoRepository.findById(itemPedidoRequest.produtoId()).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        ItemPedido save = itemPedidoRepository.save(ItemPedidoMapper.toEntity(itemPedidoRequest, pedido, produto));
        return ItemPedidoMapper.toDTO(save);
    }

    public ItemPedidoResponse atualizarPorId(Long id, ItemPedidoRequest itemPedidoRequest) {
        Pedido pedido = pedidoRepository.findById(itemPedidoRequest.pedidoId()).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Produto produto = produtoRepository.findById(itemPedidoRequest.produtoId()).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        ItemPedido entity = ItemPedidoMapper.toEntity(itemPedidoRequest, pedido, produto);
        entity.setId(id);
        return ItemPedidoMapper.toDTO(entity);
    }

    public void deletarPorId(Long id) {
        itemPedidoRepository.deleteById(id);
    }

}
