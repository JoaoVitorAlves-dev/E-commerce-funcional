package med.voll.ecommerce.domain.service;

import lombok.RequiredArgsConstructor;
import med.voll.ecommerce.domain.exceptions.IdNotFoundException;
import med.voll.ecommerce.domain.repository.ProdutoRepository;
import med.voll.ecommerce.dto.request.ProdutoRequest;
import med.voll.ecommerce.dto.response.ProdutoResponse;
import med.voll.ecommerce.entity.Produto;
import med.voll.ecommerce.mapper.ProdutoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Page<ProdutoResponse> listar(@PageableDefault(sort = "nome")Pageable pageable) {
        return produtoRepository.findByNome(pageable).map(ProdutoResponse::new);
    }

    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        return ProdutoMapper.toDTO(produto);
    }

    public ProdutoResponse adicionarProduto(ProdutoRequest produtoRequest) {
        Produto save = produtoRepository.save(ProdutoMapper.toEntity(produtoRequest));
        return ProdutoMapper.toDTO(save);
    }

    public ProdutoResponse atualizarPorId(Long id, ProdutoRequest produtoRequest) {
        produtoRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Produto entity = ProdutoMapper.toEntity(produtoRequest);
        entity.setId(id);
        Produto save = produtoRepository.save(entity);
        return ProdutoMapper.toDTO(save);
    }

    public void deletarPorId(Long id) {
        produtoRepository.deleteById(id);
    }

}
