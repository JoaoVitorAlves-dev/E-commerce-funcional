package ecom.merce.ecommerce.domain.service;

import ecom.merce.ecommerce.domain.exceptions.EstoqueInsuficienteException;
import ecom.merce.ecommerce.domain.exceptions.IdNotFoundException;
import ecom.merce.ecommerce.domain.repository.ItemPedidoRepository;
import ecom.merce.ecommerce.domain.repository.PedidoRepository;
import ecom.merce.ecommerce.domain.repository.ProdutoRepository;
import ecom.merce.ecommerce.dto.request.ItemPedidoRequest;
import ecom.merce.ecommerce.dto.response.ItemPedidoResponse;
import ecom.merce.ecommerce.entity.ItemPedido;
import ecom.merce.ecommerce.entity.Pedido;
import ecom.merce.ecommerce.entity.Produto;
import ecom.merce.ecommerce.mapper.ItemPedidoMapper;
import lombok.RequiredArgsConstructor;
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
        return itemPedidoRepository.findAllBy(pageable).map(ItemPedidoResponse::new);
    }

    public ItemPedidoResponse buscarPorId(Long id) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID não existe"));
        return ItemPedidoMapper.toDTO(itemPedido);
    }

    public ItemPedidoResponse adicionarItemPedido(ItemPedidoRequest itemPedidoRequest) {
        Pedido pedido = pedidoRepository.findById(itemPedidoRequest.pedidoId()).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Produto produto = produtoRepository.findById(itemPedidoRequest.produtoId()).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        if (produto.getEstoque() < itemPedidoRequest.quantidade()) {
            throw new EstoqueInsuficienteException("Estoque Insuficiente");
        }
        produto.setEstoque(produto.getEstoque() - itemPedidoRequest.quantidade());
        produtoRepository.save(produto);
        ItemPedido save = itemPedidoRepository.save(ItemPedidoMapper.toEntity(itemPedidoRequest, pedido, produto));
        return ItemPedidoMapper.toDTO(save);
    }

    public ItemPedidoResponse atualizarPorId(Long id, ItemPedidoRequest itemPedidoRequest) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Pedido pedido = pedidoRepository.findById(itemPedidoRequest.pedidoId()).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Produto produtoNovo = produtoRepository.findById(itemPedidoRequest.produtoId()).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Produto produtoAntigo = itemPedido.getProduto();
        produtoAntigo.setEstoque(produtoAntigo.getEstoque() + itemPedido.getQuantidade());
        produtoRepository.save(produtoAntigo);
        if (produtoNovo.getEstoque() < itemPedidoRequest.quantidade()) {
            throw new EstoqueInsuficienteException("Estoque Insuficiente");
        }
        produtoNovo.setEstoque(produtoNovo.getEstoque() - itemPedidoRequest.quantidade());
        produtoRepository.save(produtoNovo);
        ItemPedido entity = ItemPedidoMapper.toEntity(itemPedidoRequest, pedido, produtoNovo);
        entity.setId(id);
        ItemPedido save = itemPedidoRepository.save(entity);
        return ItemPedidoMapper.toDTO(save);
    }

    public void deletarPorId(Long id) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Produto produto = itemPedido.getProduto();
        produto.setEstoque(produto.getEstoque() + itemPedido.getQuantidade());
        produtoRepository.save(produto);
        itemPedidoRepository.deleteById(id);
    }

}
