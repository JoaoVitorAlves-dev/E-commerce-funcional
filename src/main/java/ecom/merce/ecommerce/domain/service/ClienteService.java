package ecom.merce.ecommerce.domain.service;

import ecom.merce.ecommerce.domain.exceptions.IdNotFoundException;
import ecom.merce.ecommerce.domain.repository.ClienteRepository;
import ecom.merce.ecommerce.dto.request.ClienteRequest;
import ecom.merce.ecommerce.dto.response.ClienteResponse;
import ecom.merce.ecommerce.entity.Cliente;
import ecom.merce.ecommerce.mapper.ClienteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<ClienteResponse> listar(@PageableDefault(sort = "nome") Pageable pageable) {
        return clienteRepository.findAllBy(pageable)
                .map(ClienteResponse::new);
    }

    public ClienteResponse buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID não existe"));
        return ClienteMapper.toDTO(cliente);
    }

    public ClienteResponse adicionarCliente(ClienteRequest clienteRequest) {
        Cliente entity = ClienteMapper.toEntity(clienteRequest);
        entity.setSenha(passwordEncoder.encode(clienteRequest.senha()));
        Cliente save = clienteRepository.save(entity);
        return ClienteMapper.toDTO(save);
    }

    public ClienteResponse atualizarCliente(Long id, ClienteRequest clienteRequest) {
        clienteRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID não existe"));
        Cliente entity = ClienteMapper.toEntity(clienteRequest);
        entity.setId(id);
        Cliente save = clienteRepository.save(entity);
        return ClienteMapper.toDTO(save);
    }

    public void deletarPorId(Long id) {
        clienteRepository.deleteById(id);
    }

}
