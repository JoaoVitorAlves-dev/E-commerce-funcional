package med.voll.ecommerce.domain.service;

import lombok.RequiredArgsConstructor;
import med.voll.ecommerce.domain.exceptions.IdNotFoundException;
import med.voll.ecommerce.domain.repository.ClienteRepository;
import med.voll.ecommerce.dto.request.ClienteRequest;
import med.voll.ecommerce.dto.response.ClienteResponse;
import med.voll.ecommerce.entity.Cliente;
import med.voll.ecommerce.mapper.ClienteMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Page<ClienteResponse> listar(@PageableDefault(sort = "nome") Pageable pageable) {
        return clienteRepository.findByNome(pageable)
                .map(ClienteResponse::new);
    }

    public ClienteResponse buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID não existe"));
        return ClienteMapper.toDTO(cliente);
    }

    public ClienteResponse adicionarCliente(ClienteRequest clienteRequest) {
        Cliente save = clienteRepository.save(ClienteMapper.toEntity(clienteRequest));
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
