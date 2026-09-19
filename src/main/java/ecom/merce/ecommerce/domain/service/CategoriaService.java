package ecom.merce.ecommerce.domain.service;

import ecom.merce.ecommerce.domain.exceptions.IdNotFoundException;
import ecom.merce.ecommerce.domain.repository.CategoriaRepository;
import ecom.merce.ecommerce.dto.request.CategoriaRequest;
import ecom.merce.ecommerce.dto.response.CategoriaResponse;
import ecom.merce.ecommerce.entity.Categoria;
import ecom.merce.ecommerce.mapper.CategoriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public Page<CategoriaResponse> listar(@PageableDefault(sort = "nome", size = 10) Pageable pageable) {
        return categoriaRepository.findByNome(pageable).map(CategoriaMapper::toDTO);
    }

    public CategoriaResponse buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        return CategoriaMapper.toDTO(categoria);
    }

    public CategoriaResponse adicionarCategoria(CategoriaRequest categoriaRequest) {
        Categoria save = categoriaRepository.save(CategoriaMapper.toEntity(categoriaRequest));
        return CategoriaMapper.toDTO(save);
    }

    public CategoriaResponse atualizarPorId(Long id, CategoriaRequest categoriaRequest) {
        categoriaRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Categoria entity = CategoriaMapper.toEntity(categoriaRequest);
        entity.setId(id);
        Categoria save = categoriaRepository.save(entity);
        return CategoriaMapper.toDTO(save);
    }

    public void deletarPorId(Long id) {
        categoriaRepository.deleteById(id);
    }

}
