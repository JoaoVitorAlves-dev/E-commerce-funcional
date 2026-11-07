package ecom.merce.ecommerce.domain.service;

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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoService pedidoService;

    public Page<ItemPedidoResponse> listar(Authentication auth, Pageable pageable) {
        return itemPedidoRepository
                .findByPedidoClienteEmail(auth.getName(), pageable)
                .map(ItemPedidoMapper::toDTO);
    }

    public ItemPedidoResponse buscarPorId(Long id, Authentication auth) {
        ItemPedido itemPedido = itemPedidoRepository
                .findByIdAndPedidoClienteEmail(id, auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ItemPedidoMapper.toDTO(itemPedido);
    }

    @Transactional
    public ItemPedidoResponse adicionarItemPedido(ItemPedidoRequest req, Authentication auth) {
        Pedido pedido = pedidoRepository.findById(req.pedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!pedido.getCliente().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Produto produto = produtoRepository.findById(req.produtoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (req.quantidade() == null || req.quantidade() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "A quantidade deve ser maior que zero.");
        }

        if (produto.getEstoque() < req.quantidade()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Estoque insuficiente.");
        }

        ItemPedido item = ItemPedidoMapper.toEntity(req, pedido, produto);
        item.setPrecoUnitario(produto.getPreco());

        produto.setEstoque(produto.getEstoque() - req.quantidade());
        produtoRepository.save(produto);

        ItemPedido salvo = itemPedidoRepository.save(item);
        pedidoService.recalcularTotalPedido(pedido.getId());

        return ItemPedidoMapper.toDTO(salvo);
    }

    @Transactional
    public ItemPedidoResponse atualizarPorId(Long id, ItemPedidoRequest req, Authentication auth) {
        ItemPedido item = itemPedidoRepository
                .findByIdAndPedidoClienteEmail(id, auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Long pedidoAntigoId = item.getPedido().getId();

        Pedido pedidoNovo = pedidoRepository.findById(req.pedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!pedidoNovo.getCliente().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Produto produtoNovo = produtoRepository.findById(req.produtoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (req.quantidade() == null || req.quantidade() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "A quantidade deve ser maior que zero.");
        }

        Produto produtoAntigo = item.getProduto();
        boolean mesmoProduto = produtoNovo.getId().equals(produtoAntigo.getId());

        int estoqueDisponivel = produtoNovo.getEstoque();
        if (mesmoProduto) {
            estoqueDisponivel += item.getQuantidade();
        }

        if (estoqueDisponivel < req.quantidade()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Estoque insuficiente.");
        }

        if (!mesmoProduto) {
            produtoAntigo.setEstoque(produtoAntigo.getEstoque() + item.getQuantidade());
            produtoRepository.save(produtoAntigo);
        }

        produtoNovo.setEstoque(estoqueDisponivel - req.quantidade());
        produtoRepository.save(produtoNovo);

        item.setPedido(pedidoNovo);
        item.setProduto(produtoNovo);
        item.setQuantidade(req.quantidade());
        item.setPrecoUnitario(produtoNovo.getPreco());

        ItemPedido salvo = itemPedidoRepository.save(item);

        pedidoService.recalcularTotalPedido(pedidoAntigoId);
        if (!pedidoAntigoId.equals(pedidoNovo.getId())) {
            pedidoService.recalcularTotalPedido(pedidoNovo.getId());
        }

        return ItemPedidoMapper.toDTO(salvo);
    }

    @Transactional
    public void deletar(Long id, Authentication auth) {
        ItemPedido item = itemPedidoRepository.findByIdAndPedidoClienteEmail(id, auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Long pedidoId = item.getPedido().getId();

        Produto produto = item.getProduto();
        produto.setEstoque(produto.getEstoque() + item.getQuantidade());
        produtoRepository.save(produto);

        itemPedidoRepository.delete(item);
        pedidoService.recalcularTotalPedido(pedidoId);
    }
}