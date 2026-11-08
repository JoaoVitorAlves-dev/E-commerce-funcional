package ecom.merce.ecommerce.domain.service;

import ecom.merce.ecommerce.domain.exceptions.IdNotFoundException;
import ecom.merce.ecommerce.domain.repository.CategoriaRepository;
import ecom.merce.ecommerce.domain.repository.ItemPedidoRepository;
import ecom.merce.ecommerce.domain.repository.ProdutoRepository;
import ecom.merce.ecommerce.dto.request.ProdutoRequest;
import ecom.merce.ecommerce.dto.response.ProdutoResponse;
import ecom.merce.ecommerce.entity.Categoria;
import ecom.merce.ecommerce.entity.Produto;
import ecom.merce.ecommerce.mapper.ProdutoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public Page<ProdutoResponse> listar(@PageableDefault(sort = "nome")Pageable pageable) {
        return produtoRepository.findAllBy(pageable).map(ProdutoResponse::new);
    }

    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = produtoRepository.findByIdForUpdate(id).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        return ProdutoMapper.toDTO(produto);
    }

    public ProdutoResponse adicionarProduto(ProdutoRequest produtoRequest) {
        Categoria categoria = categoriaRepository.findById(produtoRequest.categoriaId()).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Produto save = produtoRepository.save(ProdutoMapper.toEntity(produtoRequest, categoria));
        return ProdutoMapper.toDTO(save);
    }

    public ProdutoResponse atualizarPorId(Long id, ProdutoRequest produtoRequest) {
        produtoRepository.findByIdForUpdate(id).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Categoria categoria = categoriaRepository.findById(produtoRequest.categoriaId()).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Produto entity = ProdutoMapper.toEntity(produtoRequest, categoria);
        entity.setId(id);
        Produto save = produtoRepository.save(entity);
        return ProdutoMapper.toDTO(save);
    }

    public void deletar(Long id) {
        Produto produto = produtoRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (itemPedidoRepository.existsByProdutoId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é possível excluir um produto que já aparece em pedidos.");
        }

        produtoRepository.delete(produto);
    }

}
