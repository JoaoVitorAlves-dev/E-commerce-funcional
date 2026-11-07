package ecom.merce.ecommerce.domain.service;

import ecom.merce.ecommerce.domain.enums.RolesCliente;
import ecom.merce.ecommerce.domain.exceptions.CredenciaisInvalidasException;
import ecom.merce.ecommerce.domain.exceptions.IdNotFoundException;
import ecom.merce.ecommerce.domain.repository.ClienteRepository;
import ecom.merce.ecommerce.dto.request.ClienteRequest;
import ecom.merce.ecommerce.dto.request.LoginRequest;
import ecom.merce.ecommerce.dto.response.ClienteResponse;
import ecom.merce.ecommerce.dto.response.TokenResponse;
import ecom.merce.ecommerce.entity.Cliente;
import ecom.merce.ecommerce.mapper.ClienteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse login(LoginRequest req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.senha()));

        return new TokenResponse(tokenService.gerarToken(req.email()));
    }

    public ClienteResponse buscar(Authentication auth) {
        Cliente cliente = clienteRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ClienteMapper.toDTO(cliente);
    }

    public ClienteResponse buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Cliente não encontrado."));
        return ClienteMapper.toDTO(cliente);
    }

    public void adicionarCliente(ClienteRequest clienteRequest) {
        Cliente entity = ClienteMapper.toEntity(clienteRequest);

        if (clienteRepository.existsByEmail(clienteRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "email já cadastrado.");
        }

        entity.setSenha(passwordEncoder.encode(clienteRequest.senha()));
        entity.setRoles(RolesCliente.USER);
        clienteRepository.save(entity);
    }

    public ClienteResponse atualizarCliente(ClienteRequest clienteRequest, Authentication auth) {
        Cliente cliente = clienteRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IdNotFoundException("Cliente não encontrado."));

        if (!cliente.getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "permissões insuficientes.");
        }

        if (!cliente.getEmail().equals(clienteRequest.email()) && clienteRepository.existsByEmail(clienteRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está cadastrado.");
        }

        cliente.setEmail(clienteRequest.email());
        cliente.setNome(clienteRequest.nome());
        cliente.setSenha(passwordEncoder.encode(clienteRequest.senha()));
        cliente.setTelefone(clienteRequest.telefone());

        return ClienteMapper.toDTO(clienteRepository.save(cliente));
    }

    public void deletar(Authentication auth) {
        Cliente cliente = clienteRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IdNotFoundException("Cliente não encontrado."));
        clienteRepository.delete(cliente);
    }

}
