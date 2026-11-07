package ecom.merce.ecommerce.domain.service;

import ecom.merce.ecommerce.domain.exceptions.IdNotFoundException;
import ecom.merce.ecommerce.domain.repository.ClienteRepository;
import ecom.merce.ecommerce.domain.repository.EnderecoRepository;
import ecom.merce.ecommerce.dto.request.EnderecoRequest;
import ecom.merce.ecommerce.dto.response.EnderecoResponse;
import ecom.merce.ecommerce.entity.Cliente;
import ecom.merce.ecommerce.entity.Endereco;
import ecom.merce.ecommerce.mapper.EnderecoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;

    public Page<EnderecoResponse> listar(Pageable pageable, Authentication auth) {
        return enderecoRepository.findByClienteEmail(auth.getName(), pageable).map(EnderecoMapper::toDTO);
    }

    public EnderecoResponse buscarPorId(Long id) {
        Endereco endereco = enderecoRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        return EnderecoMapper.toDTO(endereco);
    }

    public EnderecoResponse adicionarEndereco(EnderecoRequest enderecoRequest, Authentication auth) {
        Cliente clienteId = clienteRepository.findById(enderecoRequest.clienteId())
                .orElseThrow(() -> new IdNotFoundException("ID Não existe"));

        if (!clienteId.getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Endereco save = enderecoRepository.save(EnderecoMapper.toEntity(enderecoRequest, clienteId));
        return EnderecoMapper.toDTO(save);
    }

    public EnderecoResponse atualizar(EnderecoRequest req, Authentication auth) {
        Endereco endereco = enderecoRepository.findByClienteEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        endereco.setRua(req.rua());
        endereco.setCep(req.cep());
        endereco.setCidade(req.cidade());
        endereco.setEstado(req.estado());

        return EnderecoMapper.toDTO(enderecoRepository.save(endereco));
    }

    public void deletar(Authentication auth) {
        Endereco endereco = enderecoRepository.findByClienteEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        enderecoRepository.delete(endereco);
    }

}
