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
import org.hibernate.internal.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public Page<ItemPedidoResponse> listar(Authentication auth, Pageable pageable) {
        return itemPedidoRepository.findByPedidoClienteEmail(auth.getName(), pageable).map(ItemPedidoMapper::toDTO);
    }

    public ItemPedidoResponse buscarPorId(Long id) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID não existe"));
        return ItemPedidoMapper.toDTO(itemPedido);
    }

    public ItemPedidoResponse adicionarItemPedido(ItemPedidoRequest itemPedidoRequest, Authentication auth) {
        ItemPedido itemPedido = itemPedidoRepository.findById(itemPedidoRequest.pedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!itemPedido.getPedido().getCliente().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Produto produto = produtoRepository.findById(itemPedidoRequest.pedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Pedido pedido = pedidoRepository.findById(itemPedidoRequest.pedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (produto.getEstoque() < itemPedidoRequest.quantidade()) {
            throw new EstoqueInsuficienteException("Estoque Insuficiente");
        }

        produto.setEstoque(produto.getEstoque() - itemPedidoRequest.quantidade());
        produtoRepository.save(produto);
        return ItemPedidoMapper.toDTO(itemPedidoRepository.save(ItemPedidoMapper.toEntity(itemPedidoRequest, pedido, produto)));
    }

    public ItemPedidoResponse atualizarPorId(Long id, ItemPedidoRequest req, Authentication auth) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!itemPedido.getPedido().getCliente().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Pedido pedido = pedidoRepository.findById(req.pedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Produto produto = produtoRepository.findById(req.produtoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        itemPedido.setPrecoUnitario(req.precoUnitario());
        itemPedido.setQuantidade(req.quantidade());
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);

        return ItemPedidoMapper.toDTO(itemPedidoRepository.save(itemPedido));
    }

    public void deletar(Authentication auth) {
        ItemPedido itemPedido = itemPedidoRepository.findByPedidoClienteEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        itemPedidoRepository.delete(itemPedido);
    }

}
