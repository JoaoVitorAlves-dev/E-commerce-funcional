package ecom.merce.ecommerce.domain.service;

import ecom.merce.ecommerce.domain.exceptions.IdNotFoundException;
import ecom.merce.ecommerce.domain.repository.EnderecoRepository;
import ecom.merce.ecommerce.dto.request.EnderecoRequest;
import ecom.merce.ecommerce.dto.response.EnderecoResponse;
import ecom.merce.ecommerce.entity.Endereco;
import ecom.merce.ecommerce.mapper.EnderecoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public Page<EnderecoResponse> listar(@PageableDefault(sort = "rua", size = 10) Pageable pageable) {
        return enderecoRepository.findByRua(pageable).map(EnderecoResponse::new);
    }

    public EnderecoResponse buscarPorId(Long id) {
        Endereco endereco = enderecoRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        return EnderecoMapper.toDTO(endereco);
    }

    public EnderecoResponse adicionarEndereco(EnderecoRequest enderecoRequest) {
        Endereco save = enderecoRepository.save(EnderecoMapper.toEntity(enderecoRequest));
        return EnderecoMapper.toDTO(save);
    }

    public EnderecoResponse atualizarPorId(Long id, EnderecoRequest enderecoRequest) {
        enderecoRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Endereco entity = EnderecoMapper.toEntity(enderecoRequest);
        entity.setId(id);
        Endereco save = enderecoRepository.save(entity);
        return EnderecoMapper.toDTO(save);
    }

    public void deletarPorId(Long id) {
        enderecoRepository.deleteById(id);
    }

}
